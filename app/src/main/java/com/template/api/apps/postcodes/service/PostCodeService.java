package com.template.api.apps.postcodes.service;


import com.template.api.apps.postcodes.dto.AptPriceDto.Official;
import com.template.api.apps.postcodes.dto.AptPriceDto.OfficialItemDong;
import com.template.api.apps.postcodes.dto.AptPriceDto.OfficialItemHo;
import com.google.common.collect.Lists;
import com.template.api.apps.logs.domain.LogFileUpload;
import com.template.api.apps.logs.dto.LogFileUploadDto;
import com.template.api.apps.logs.service.LogFileUploadService;
import com.template.api.apps.postcodes.domain.*;
import com.template.api.apps.postcodes.dto.*;
import com.template.api.jpa.Restrictions;
import com.template.api.jpa.Restrictions.Conn;
import com.opencsv.CSVReader;
import com.template.api.apps.postcodes.domain.AptSales.Type;
import com.template.api.apps.postcodes.domain.AptSales.Method;


import com.template.api.apps.postcodes.dto.PostcodeDto.Request;
import com.template.api.apps.postcodes.dto.PostcodeDto.Response;
import com.template.api.apps.products.domain.AreaType;
import com.template.api.apps.products.domain.AreaTypeRepository;
import com.template.api.jpa.Restrictions;
import com.template.api.utils.ActUtils;
import com.template.api.utils.dtos.PagableDto;
import lombok.RequiredArgsConstructor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PostCodeService {

    private final RestTemplate restTemplate;

    private final AptComplexRepository aptComplexRepository;
    private final AptPriceRepository aptPriceRepository;
    private final AptSizeTypeRepository aptSizeTypeRepository;
    private final AreaTypeRepository areaTypeRepository;

    private final PostcodeRepository postcodeRepository;
    private final PostcodeRoadNameRepository postcodeRoadNameRepository;
    private final OfficialLandRepository officialLandRepository;

    private final LogFileUploadService logFileUploadService;

    private final AptSalesRepository aptSalesRepository;

    private ModelMapper mapper = new ModelMapper();

    private final String uploadName = "KbUpload";

    private String ns(String b) {
        return b == null ? "" : b;
    }

    private String ps(Object o) {
        return o == null ? null : String.valueOf(o);
    }

    private Long pl(Object o) {
        if (o == null) {
            return null;
        }
        String r = String.valueOf(o).replaceAll("[^0-9\\.]+", "");
        return StringUtils.isEmpty(r) ? null : Long.parseLong(r, 10);
    }

    /**
     * zip파일 히스토리 선처리
     */
    @Transactional
    public void zipFileUploadStart(String zipName, MultipartFile file) throws IOException {

        ZipInputStream zipStream = null;

        try {
            zipStream = new ZipInputStream(
                    new BufferedInputStream(file.getInputStream(), 8192),
                    Charset.forName("EUC-KR")
            );

            ZipEntry ze;
            while ((ze = zipStream.getNextEntry()) != null) {
                if (ze.isDirectory()) {
                    continue;
                }

                ByteArrayInputStream stream = new ByteArrayInputStream(zipStream.readAllBytes());
                String myString = IOUtils.toString(stream, "EUC-KR");
                List<String> rows = Lists.newArrayList(myString.split("\\n"));

                if (ze.getName().contains("정보") || ze.getName().contains("시세") || ze.getName()
                        .contains("평형")) {
                    LogFileUploadDto.Create logCreate = new LogFileUploadDto.Create();
                    logCreate.setUploadName(uploadName);
                    logCreate.setUploadZipName(zipName);
                    logCreate.setUploadFileName(ze.getName());
                    logCreate.setUploadStatus(LogFileUpload.UploadStatusType.WAIT);
                    logFileUploadService.create(logCreate);
                }
            }
        } finally {
            if (zipStream != null) {
                zipStream.close();
            }
        }
    }

    /**
     * zip파일 import 1.정보, 2.시세, 3.평형 4.정보 First 실행하기.
     */
    public List<PostcodeDto.UploadDto> zipFileImport(String zipName, MultipartFile file)
            throws IOException {

        ZipInputStream zipStream = null;
        List<PostcodeDto.UploadDto> rowsList = Lists.newArrayList();
        try {
            zipStream = new ZipInputStream(
                    new BufferedInputStream(file.getInputStream(), 8192),
                    Charset.forName("EUC-KR")
            );

            ZipEntry ze;
            while ((ze = zipStream.getNextEntry()) != null) {
                if (ze.isDirectory()) {
                    continue;
                }

                ByteArrayInputStream stream = new ByteArrayInputStream(zipStream.readAllBytes());
                String myString = IOUtils.toString(stream, "EUC-KR");
                List<String> rows = Lists.newArrayList(myString.split("\\n"));

                if (ze.getName().contains("정보")) {
                    rowsList.add(new PostcodeDto.UploadDto(0, zipName, ze.getName(), rows));
                } else if (ze.getName().contains("시세")) {
                    rowsList.add(new PostcodeDto.UploadDto(1, zipName, ze.getName(), rows));
                } else if (ze.getName().contains("평형")) {
                    rowsList.add(new PostcodeDto.UploadDto(2, zipName, ze.getName(), rows));
                }
            }
        } finally {
            if (zipStream != null) {
                zipStream.close();
            }
        }

        return rowsList;
    }

    /**
     * 정보 업로드
     */
    @Transactional
    public void applyToAptComplex(String zipName, String fileName, List<String> rows) {
        List<AptComplex> aptComplexes = aptComplexRepository.findAll();

        LogFileUploadDto.CmsResponse logFileUpload = null;

        for (String row : rows) {
            int nowCnt = rows.indexOf(row) + 1;
            double per = (Math.floor((nowCnt * 1000 / rows.size())) / 10);

            System.out.println(
                    "AptComplex Upload Processing.. " + per + "% / " + nowCnt + " : " + rows.size());

            if (logFileUpload == null) {
                logFileUpload = logFileUploadService.getLogFileUpload(
                        uploadName,
                        zipName,
                        fileName,
                        LogFileUpload.UploadStatusType.WAIT
                );
            }

//            if (logFileUpload != null) {
//                LogFileUploadDto.Update logUpdate = new LogFileUploadDto.Update();
//                logUpdate.setProcessPer(per);
//                logUpdate.setProcessCnt(nowCnt);
//                logUpdate.setUploadStatus(LogFileUpload.UploadStatusType.PROCESSING);
//                logFileUpload = logFileUploadService.update(logFileUpload.getId(), logUpdate);
//            }

            List<String> cols = Lists.newArrayList(row.split("\\|"));
            Map<String, String> item = new HashedMap();
            for (int i = 0; i < AptComplexDto.FIELDS.size(); i++) {
                item.put(AptComplexDto.FIELDS.get(i), cols.get(i));
            }

            Optional<AptComplex> oAptComplex = aptComplexes
                    .stream()
                    .filter(v -> v.getId().equals(item.get("id")))
                    .findAny();

            AptComplex aptComplex = oAptComplex.orElse(new AptComplex());

            boolean isChanged =
                    item.getOrDefault("addr1", "").equals(aptComplex.getAddr1()) ||
                            item.getOrDefault("addr2", "").equals(aptComplex.getAddr2()) ||
                            item.getOrDefault("addr3", "").equals(aptComplex.getAddr3()) ||
                            item.getOrDefault("addr4", "").equals(aptComplex.getAddr4()) ||
                            item.getOrDefault("addr5", "").equals(aptComplex.getAddr5()) ||
                            item.getOrDefault("addr6", "").equals(aptComplex.getAddr6()) ||
                            item.getOrDefault("addr8", "").equals(aptComplex.getAddr7()) ||
                            item.getOrDefault("roadAddress", "").equals(aptComplex.getRoadAddress()) ||
                            item.getOrDefault("roadAddressDetails", "").equals(aptComplex.getRoadAddressDetails());

            if (oAptComplex.isEmpty()) {
                //없으면 생성.
                aptComplexes.add(aptComplex);
            }
            // 전체 저장 필요

            String roadName = aptComplex.getRoadAddress();
            mapper.map(item, aptComplex);
            // KB 시세 새로올때 보면 도로명이 다시 올떄가 있음
            if (StringUtils.isEmpty(aptComplex.getRoadAddress().trim()) &&
                    StringUtils.isNotEmpty(roadName)
            ) {
                aptComplex.setRoadAddress(roadName);
            }

            if (isChanged ||
                    StringUtils.isEmpty(aptComplex.getSearchText()) ||
                    StringUtils.isEmpty(aptComplex.getSearchTextRoad())) {

                Postcode postcode = null;
                String bcodeDetails = aptComplex.getRoadBuildMgrNo();
                if (StringUtils.isNotEmpty(bcodeDetails) && bcodeDetails.length() > 5) {
                    bcodeDetails = bcodeDetails.substring(0, bcodeDetails.length() - 3) + "%";
                    Restrictions postcodeRe = new Restrictions();
                    postcodeRe.like("mid", bcodeDetails);
                    Page<Postcode> postcodes = postcodeRepository.findAll(
                            postcodeRe.output(),
                            PageRequest.of(0, 1)
                    );
                    if (postcodes.getTotalElements() > 0) {
                        postcode = postcodes.getContent().get(0);
                    }
                }

                if (postcode != null) {
                    roadName =
                            postcode.getSido() + " " + ns(postcode.getSigungu()) + " " + postcode.getRoadName();
                    roadName = roadName.replaceAll("\\s+", " ");
                } else {
                    String rc = aptComplex.getRoadCode();
                    List<PostcodeRoadName> rnes = postcodeRoadNameRepository.findTop1ByIdId(rc);
                    if (rnes.size() > 0) {
                        PostcodeRoadName rne = rnes.get(0);
                        roadName = rne.getSido() + " " + ns(rne.getSigungu()) + " " + rne.getRoadName();
                        roadName = roadName.replaceAll("\\s+", " ");
                    }

                }
                aptComplex.setRoadAddress(roadName);

                String searchText = "";
                searchText += aptComplex.getAddr1() + " ";
                searchText += ns(aptComplex.getAddr2()) + " ";
                searchText += ns(aptComplex.getAddr3()) + " ";
                searchText += ns(aptComplex.getAddr4()) + " ";
                searchText += ns(aptComplex.getAddr5()) + " ";
                if (postcode != null) {
                    searchText += ns(postcode.getRi()) + " ";
                }
                searchText += ns(aptComplex.getAddr6()) + " ";
                searchText += ns(aptComplex.getAddr7()) + " ";
                if (postcode != null) {
                    searchText += ns(postcode.getBuildName()) + " ";
                }
                searchText += ns(aptComplex.getName());
                aptComplex.setSearchText(searchText.toUpperCase().replaceAll("\\s+", " "));

                String searchTextRoad = "";
                searchTextRoad += ns(aptComplex.getRoadAddress()) + " ";
                searchTextRoad += ns(aptComplex.getRoadAddressDetails()) + " ";
                if (postcode != null) {
                    searchTextRoad += ns(postcode.getBuildName()) + " ";
                }
                searchTextRoad += ns(aptComplex.getName());
                aptComplex.setSearchTextRoad(searchTextRoad.toUpperCase().replaceAll("\\s+", " "));
            } else {
                aptComplex.setRoadAddress(roadName);
            }

            if (logFileUpload != null && nowCnt == rows.size()) {
                LogFileUploadDto.Update logUpdate = new LogFileUploadDto.Update();
                logUpdate.setTotalCnt(rows.size());
                logUpdate.setProcessPer(per);
                logUpdate.setProcessCnt(nowCnt);
                logUpdate.setUploadStatus(LogFileUpload.UploadStatusType.FINISH);
                logFileUploadService.update(logFileUpload.getId(), logUpdate);
            }
        }
        aptComplexRepository.saveAll(aptComplexes);

//    this.updateKbIds(rows.size() + 10);
    }

    /**
     * 시세 업로드
     */
    @Transactional
    public void applyToAptPrice(String zipName, String fileName, List<String> rows) {

        aptPriceRepository.deleteAll();

        LogFileUploadDto.CmsResponse logFileUpload = null;

        for (String row : rows) {
            int nowCnt = rows.indexOf(row) + 1;
            double per = (Math.floor((nowCnt * 1000 / rows.size())) / 10);

            System.out.println(
                    "AptPrice Upload Processing.. " + per + "% / " + nowCnt + " : " + rows.size());

            if (logFileUpload == null) {
                logFileUpload = logFileUploadService.getLogFileUpload(uploadName, zipName, fileName,
                        LogFileUpload.UploadStatusType.WAIT);
            }

            List<String> cols = Lists.newArrayList(row.split("\\|"));
            Map<String, String> item = new HashedMap();
            for (int i = 0; i < AptPriceDto.FIELDS.size(); i++) {
                item.put(AptPriceDto.FIELDS.get(i), cols.get(i));
            }
            AptPrice aptPrice = mapper.map(item, AptPrice.class);
            aptPrice.setId(new AptPrice.PK(
                    item.get("idDate"),
                    aptComplexRepository.getById(item.get("idAptComplexId")),
                    Integer.parseInt(item.get("idSizeNo"))
            ));
            System.out.println(aptPrice.getId());

            aptPriceRepository.save(aptPrice);

            if (logFileUpload != null && nowCnt == rows.size()) {
                LogFileUploadDto.Update logUpdate = new LogFileUploadDto.Update();
                logUpdate.setTotalCnt(rows.size());
                logUpdate.setProcessPer(per);
                logUpdate.setProcessCnt(nowCnt);
                logUpdate.setUploadStatus(LogFileUpload.UploadStatusType.FINISH);
                logFileUploadService.update(logFileUpload.getId(), logUpdate);
            }
        }
    }

    /**
     * 평형 업로드
     */
    @Transactional
    public void applyToAptSizeType(String zipName, String fileName, List<String> rows) {

        aptSizeTypeRepository.deleteAll();

        LogFileUploadDto.CmsResponse logFileUpload = null;

        for (String row : rows) {
            int nowCnt = rows.indexOf(row) + 1;
            double per = (Math.floor((nowCnt * 1000 / rows.size())) / 10);

            System.out.println(
                    "AptSizeType Upload Processing.. " + per + "% / " + nowCnt + " : " + rows.size());

            if (logFileUpload == null) {
                logFileUpload = logFileUploadService.getLogFileUpload(uploadName, zipName, fileName,
                        LogFileUpload.UploadStatusType.WAIT);
            }

            List<String> cols = Lists.newArrayList(row.split("\\|"));
            Map<String, String> item = new HashedMap();
            for (int i = 0; i < AptSizeTypeDto.FIELDS.size(); i++) {
                item.put(AptSizeTypeDto.FIELDS.get(i), cols.get(i));
            }
            AptSizeType aptSizeType = mapper.map(item, AptSizeType.class);
            aptSizeType.setId(new AptSizeType.PK(
                    aptComplexRepository.getById(item.get("idAptComplexId")),
                    Integer.parseInt(item.get("idSizeNo"))
            ));
            System.out.println(aptSizeType.getId());
            aptSizeTypeRepository.save(aptSizeType);

            if (logFileUpload != null && nowCnt == rows.size()) {
                LogFileUploadDto.Update logUpdate = new LogFileUploadDto.Update();
                logUpdate.setTotalCnt(rows.size());
                logUpdate.setProcessPer(per);
                logUpdate.setProcessCnt(nowCnt);
                logUpdate.setUploadStatus(LogFileUpload.UploadStatusType.FINISH);
                logFileUploadService.update(logFileUpload.getId(), logUpdate);
            }
        }
    }

    /**
     * 정보 업로드 First
     */
    @Transactional
    public int updateKbIds() {

        Restrictions r = new Restrictions();
        r.isNull("kbCode");

//        PageRequest pageRequest = PageRequest.of(0, limit, Sort.by(Order.asc("updatedOn")));
//        Page<AptComplex> aptComplexes = aptComplexRepository.findAll(r.output(), pageRequest);
        List<AptComplex> aptComplexes = aptComplexRepository.findAll(r.output(),
                Sort.by(Sort.Order.asc("updatedOn")));

        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(headers);

        int i = 0;
        double processingCnt = 0.0;
        for (AptComplex complex : aptComplexes) {
            try {
                String uri = "https://api.kbland.kr/land-complex/complex/complexMapping?시세물건식별자=";
                ResponseEntity<JSONObject> out =
                        restTemplate.exchange(
                                uri + complex.getId(),
                                HttpMethod.GET,
                                entity,
                                JSONObject.class
                        );

                complex.setKbCode(
                        out.getBody()
                                .getJSONObject("dataBody")
                                .getJSONObject("data")
                                .getString("단지기본일련번호")
                );
                aptComplexRepository.save(complex);
                Thread.sleep(100);
                i++;

                double prc = (Math.floor((i * 1000) / aptComplexes.size()) / 10);

                if (processingCnt < prc) {
                    processingCnt = prc;
                    System.out.println(
                            "updateKbIds Processing.. "
                                    + prc
                                    + "%"
                    );
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        System.out.println("updateKbIds Processing.. complate!!!!");

        return i;

    }


    private final static Integer LIMIT = 100;


    public List<Response> findByReq(Request request) {

        String keyword = request.getKeyword()
                .replaceAll("\\s+", "")
                .replaceAll("|", "%");

        String id = null;
        String isVilla = null;
        if (StringUtils.isNotEmpty(request.getKbid2())) {
            id = request.getKbid2();
        } else if (StringUtils.isNotEmpty(request.getType())) {
            if ("LNIO001".equals(request.getType())) {
                // 아파트
                id = "KBA%";
                isVilla = "F";
            } else if ("LNIO003".equals(request.getType())) {
                // 오피스텔
                id = "KBO%";
                isVilla = "F";
            } else if ("LNIO002".equals(request.getType())) {
                // 빌라
                isVilla = "T";
            } else {
                id = "KBX%";
            }
        }

//    if("T".equals(isVilla)) {
//      buildType = "VILLA";
//    } else if("")

        List<PostcodeDto.Response> items = Lists.newArrayList();

        List<String> bcodes = Lists.newArrayList();
        List<String> notMids = Lists.newArrayList();
        List<String> notAddrs = Lists.newArrayList();
        List<String> notNames = Lists.newArrayList();

        List<Map<String, Object>> o = aptComplexRepository.getSearchData(keyword, id, isVilla,
                request.getKeyword(), LIMIT);
        for (Map<String, Object> aptComplex : o) {
            PostcodeDto.Response row = new PostcodeDto.Response();

            String name = ps(aptComplex.get("name"));
            if (name != null) {
                name = name.replaceAll("\\s+", " ");
                if (name.endsWith(".")) {
                    name = name.substring(0, name.length() - 1);
                }
            }

            row.setName(name);
            row.setLabel(name);
            if (!"T".equals(aptComplex.get("is_ticket"))) {
                row.setAddress(
                        aptComplex.get("road_address") + " " + aptComplex.get("road_address_details"));
            } else {
                row.setLabel(name + " (분양권)");
            }
            row.setKbId(ps(aptComplex.get("kb_code")));
            row.setKbId2(ps(aptComplex.get("id")));
            row.setBcode(ps(aptComplex.get("bcode")));
            row.setTotalLive(pl(String.valueOf(aptComplex.get("total_live"))));
            row.setAddrId(ps(aptComplex.get("road_build_mgr_no")));
            String buildType = null;

            // 빌라
            if (aptComplex.get("is_villa") != null && aptComplex.get("is_villa").equals("T")) {
                buildType = "VILLA";
            }
            //아파트
            else if (aptComplex.get("is_villa") != null && aptComplex.get("is_villa").equals("F")) {
                buildType = "APART";
            }
            //오피스텔
            if (aptComplex.get("id") != null && aptComplex.get("id").toString().startsWith("KBO")) {
                buildType = "OFFICE-TEL";
            }

            row.setBuildType(buildType);
            String lowestLabel = "";
            //if(row.getName())
            // 추가된부분 이슈 #2532
            String label = "";
//            String[] addrs = row.getAddress().split(" ");
//            //건물이름 있을때
//            if (StringUtils.isNotEmpty(name)) {
//                for (String item : addrs) {
//                    if (item.endsWith("군")) label.append(item).append(" ");
//                    if (item.endsWith("구")) label.append(item).append(" ");
//                    if (item.endsWith("동")) label.append(item).append(" ");
//                    if (item.endsWith("로")) label.append(item).append(" ");
//                }
//                label.append(name);
//                // 건물이름 없을때
//            } else {
//                for(String item : addrs) {
//                    if(item.endsWith("시")) continue;
//                    if(item.endsWith("도")) continue;
//                    label.append(item).append(" ");
//                }
//            }
            String addr2 = ps(aptComplex.get("addr2"));
            String addr3 = ps(aptComplex.get("addr3"));
            String addr4 = ps(aptComplex.get("addr4"));
            String addr5 = ps(aptComplex.get("addr5"));
            String addr6 = ps(aptComplex.get("addr6"));
            String addr7 = ps(aptComplex.get("addr7"));

            // 건물이름이 있을경우
            if (StringUtils.isNotEmpty(name)) {
                // lowestLabel
                // 동 , 읍 주소가 널이 아닐때
                if (addr4 != null) {
                    // addr4의 마지막 글자가 동이고, addr5 가 널이거나 빈문자일때
//          if(addr4.endsWith("동") && StringUtils.isBlank(addr5)) {
//            lowestLabel += addr4 + " ";
//            lowestLabel += name;
//          }
                    // addr5가 빈문자가 아니고, 읍으로 끝날때
                    if (StringUtils.isNotBlank(addr5) && addr4.endsWith("읍")) {
                        lowestLabel += addr5 + " ";
                        lowestLabel += name;
                        // 이외 모든케이스
                    } else {
                        lowestLabel += addr4 + " ";
                        lowestLabel += name;
                    }


                }
                if (addr2 != null && !addr2.isBlank() && addr3 != null && !addr3.isBlank()) {
                    label += (addr3 + " ");
                } else {
                    if (addr2 != null && !addr2.isBlank() && addr2.equals("null")) {
                        label += (addr2 + " ");
                    }
                    if (addr3 != null && !addr3.isBlank()) {
                        label += (addr3 + " ");
                    }
                }
                if (addr4 != null && !addr4.isBlank()) {
                    label += (addr4 + " ");
                }
                if (addr5 != null && !addr5.isBlank()) {
                    label += (addr5 + " ");
                }
                label += (name);


                // 건물이름이 없을경우
            } else {
                lowestLabel = row.getAddress();
                if (addr2 != null && !addr2.isBlank()) {
                    label += (addr2 + " ");
                }
                if (addr3 != null && !addr3.isBlank()) {
                    label += (addr3 + " ");
                }
                if (addr4 != null && !addr4.isBlank()) {
                    label += (addr4 + " ");
                }
                if (addr5 != null && !addr5.isBlank()) {
                    label += (addr5 + " ");
                }
                if (addr6 != null && !addr6.isBlank()) {
                    label += (addr6 + " ");
                }
                if (addr7 != null && !addr7.isBlank()) {
                    label += (addr7 + " ");
                }
            }
            if (label.contains("null")) {
                label = label.replace("null", "").trim();
            }
            row.setLabel(label);
            row.setLowestLabel(lowestLabel);
            notNames.add(name.replaceAll("\\s+", "").toUpperCase());

            bcodes.add(row.getBcode().substring(0, 8));
            bcodes.add(row.getBcode().substring(0, 5));
            bcodes.add(row.getBcode().substring(0, 4) + "0");
            bcodes.add(row.getBcode().substring(0, 2));

            if (StringUtils.isNotEmpty(row.getAddrId()) && row.getAddrId().length() >= 8) {
                bcodes.add(row.getAddrId().substring(0, 8));
                bcodes.add(row.getAddrId().substring(0, 5));
                bcodes.add(row.getAddrId().substring(0, 4) + "0");
                bcodes.add(row.getAddrId().substring(0, 2));
            }

            if (!StringUtils.isNotEmpty(String.valueOf(aptComplex.get("road_build_mgr_no")))
                    && String.valueOf(aptComplex.get("road_build_mgr_no")).length() > 3) {
                notMids.add(
                        String.valueOf(aptComplex.get("road_build_mgr_no"))
                                .substring(0, String.valueOf(aptComplex.get("road_build_mgr_no")).length() - 3)
                                + "%"
                );
            }
            String subaddr = row.getAddress().replaceAll("\\s+", "");
            if (subaddr.indexOf("(") > 0) {
                subaddr = subaddr.substring(0, subaddr.indexOf("("));
            }
            if (subaddr.indexOf(",") > 0) {
                subaddr = subaddr.substring(0, subaddr.indexOf(","));
            }
            notAddrs.add(subaddr);
            items.add(row);
        }

        if (items.size() < LIMIT && StringUtils.isEmpty(request.getKbid2())) {
            PageRequest pr = PageRequest.of(
                    0, LIMIT - items.size(),
                    Sort.by(
                            Sort.Order.asc("buildName"),
                            Sort.Order.asc("mid"),
                            Sort.Order.asc("id")
                    )
            );

            Restrictions rp = new Restrictions();
            Restrictions keyR2 = new Restrictions(Restrictions.Conn.OR);
            keyR2.like("buildName", keyword);
            keyR2.like("searchText", keyword.toUpperCase());
            keyR2.like("searchTextRoad", keyword.toUpperCase());
            rp.addChild(keyR2);

//      rp.eq("master", Boolean.TRUE);

            for (String notMid : notMids) {
                rp.nlike("mid", notMid);
            }

            if (StringUtils.isNotEmpty(request.getMid())) {
                rp.eq("mid", request.getMid());
            }

            if (StringUtils.isNotEmpty(request.getType())) {
                if ("LNIO001".equals(request.getType())) {
                    // 아파트
                    rp.eq("type", "1");
                } else {
                    rp.ne("type", "1");
                }

                // 단중과장 요청 임시제거
//                if ("LNIO002".equals(request.getType())) {
//                    // 빌라
//                    rp.eq("type", "2");
//                }
            }

            Page<Postcode> postcodes = postcodeRepository.findAll(rp.output(), pr);

            List<String> mids = postcodes.getContent()
                    .stream()
                    .filter(x -> x.getMaster()).map(x -> x.getMid())
                    .collect(Collectors.toList());

            postcodes.getContent().stream()
                    .filter(v -> {
                        String uname = StringUtils.isEmpty(v.getBuildName()) ? ""
                                : v.getBuildName().replaceAll("\\s+", "").toUpperCase();
                        Long size = StringUtils.isEmpty(v.getBuildName()) ? 0 : notNames.stream().filter(t -> {
                            return uname.startsWith(t) || t.startsWith(uname);
                        }).count();
                        return (v.getMaster() || !mids.contains(v.getMid())) && size <= 0;
                    })
                    .forEach(item -> {
                        String address = item.getSido();
                        if (StringUtils.isNotEmpty(item.getSigungu())) {
                            address += " " + item.getSigungu();
                        }
                        if (StringUtils.isNotEmpty(item.getRoadName())) {
                            address += " " + item.getRoadName();
                        }
                        if (StringUtils.isNotEmpty(item.getBuildMainCode())) {
                            address += " " + item.getBuildMainCode();
                        }
                        if (StringUtils.isNotEmpty(item.getBuildSubCode()) && !"0".equals(
                                item.getBuildSubCode())) {
                            address += "-" + item.getBuildSubCode();
                        }

                        address += " (" + item.getDong() + ")";

                        Response row = new Response();
                        row.setAddrId(item.getMid());
                        row.setName(item.getBuildName());
                        row.setAddress(address);
                        row.setBcode(item.getBcode());
//                        row.setId(item.getId());

                        // 추가된부분 이슈 #2532
                        String label = "";
                        String lowestLabel = "";
                        if (StringUtils.isNotBlank(item.getBuildName()) && item.getBuildName() != null) {
                            if (StringUtils.isNotBlank(item.getSigungu()) && item.getSigungu() != null) {
                                label += item.getSigungu() + " ";
                            }
                            if (StringUtils.isNotBlank(item.getDong()) && item.getDong() != null) {
                                label += item.getDong() + " ";
                                // #3827 이슈
                                lowestLabel += item.getDong() + " ";
                            }
                            label += item.getBuildName();
                            lowestLabel += item.getBuildName();
                            row.setLabel(label.trim());
                        } else {
                            //label += item.getSido() + " " + item.getSigungu() + " " + item.getDong();
                            label += row.getAddress().replace(item.getSido(), "");
                            row.setLabel(label.trim() + " ");

                            //# 3827 이슈
                            lowestLabel += row.getAddress();
                        }
                        row.setLowestLabel(lowestLabel);
                        bcodes.add(row.getBcode().substring(0, 8));
                        bcodes.add(row.getBcode().substring(0, 5));
                        bcodes.add(row.getBcode().substring(0, 4) + "0");
                        bcodes.add(row.getBcode().substring(0, 2));

                        if (!StringUtils.isNotEmpty(row.getAddrId()) && row.getAddrId().length() >= 8) {
                            bcodes.add(row.getAddrId().substring(0, 8));
                            bcodes.add(row.getAddrId().substring(0, 5));
                            bcodes.add(row.getAddrId().substring(0, 4) + "0");
                            bcodes.add(row.getAddrId().substring(0, 2));
                        }

                        String subaddr = address.replaceAll("\\s+", "");
                        if (subaddr.indexOf("(") > 0) {
                            subaddr = subaddr.substring(0, subaddr.indexOf("("));
                        }
                        if (subaddr.indexOf(",") > 0) {
                            subaddr = subaddr.substring(0, subaddr.indexOf(","));
                        }
                        if (!notAddrs.contains(subaddr)) {
                            items.add(row);
                        }
                    });
        }

        List<AreaType> areaTypes = areaTypeRepository.getByBcodes(bcodes);

        for (int i = 0; i < items.size(); i++) {
            Response item = items.get(i);

            AreaType areaType = areaTypes.stream().filter(x ->
                    x.getBcode().equals(item.getBcode().substring(0, 8))
            ).findFirst().orElse(null);

            if (areaType == null) {
                areaType = areaTypes.stream().filter(x ->
                        x.getBcode().equals(item.getBcode().substring(0, 5))
                ).findFirst().orElse(null);
            }

            if (areaType == null) {
                areaType = areaTypes.stream().filter(x ->
                        x.getBcode().equals(item.getBcode().substring(0, 4) + "0")
                ).findFirst().orElse(null);
            }

            if (areaType == null) {
                areaType = areaTypes.stream().filter(x ->
                        x.getBcode().equals(item.getBcode().substring(0, 2))
                ).findFirst().orElse(null);
            }

            if (item.getAddrId() != null && item.getAddrId().length() >= 8 && areaType == null) {
                if (areaType == null) {
                    areaType = areaTypes.stream().filter(x ->
                            x.getBcode().equals(item.getAddrId().substring(0, 8))
                    ).findFirst().orElse(null);
                }

                if (areaType == null) {
                    areaType = areaTypes.stream().filter(x ->
                            x.getBcode().equals(item.getAddrId().substring(0, 5))
                    ).findFirst().orElse(null);
                }

                if (areaType == null) {
                    areaType = areaTypes.stream().filter(x ->
                            x.getBcode().equals(item.getAddrId().substring(0, 4) + "0")
                    ).findFirst().orElse(null);
                }

                if (areaType == null) {
                    areaType = areaTypes.stream().filter(x ->
                            x.getBcode().equals(item.getAddrId().substring(0, 2))
                    ).findFirst().orElse(null);
                }
            }

            item.setSpecType(areaType == null ? "ARTY004"
                    : areaType.getType() == null ? "ARTY004" : areaType.getType().toString());
        }

        if (ActUtils.isNotBlank(request.getKeyword())) {
            String ignore = request.getKeyword().replaceAll("%", "");
            // 라벨 , 주소지, 네임중 키워드가 있어야 우선으로 나오게
            List<Response> another = items.stream()
                    .filter(v -> v.getName() != null && v.getLabel() != null && v.getAddress() != null)
                    .filter((v) ->
                            (v.getLabel() == null || !v.getLabel().contains(ignore)) &&
                                    (v.getAddress() == null || !v.getAddress().contains(ignore)) &&
                                    (v.getName() == null || !v.getName().contains(ignore))
                    ).collect(Collectors.toList());
            items.removeAll(another);
            items.addAll(another);
        }

        return items;
    }

    public List<AptPriceDto.Simple> getSimpleByKbId(String kbId) {

        AptComplex aptComplex = aptComplexRepository.getById(kbId);

        Restrictions r = new Restrictions();
        r.eq("id.aptComplex", aptComplex);

        List<AptSizeType> types = aptSizeTypeRepository.findAll(r.output(),
                Sort.by(Sort.Order.asc("size"), Sort.Order.asc("type")));
        List<AptPrice> prices = aptPriceRepository.findAll(r.output());

        List<AptPriceDto.Simple> items = Lists.newArrayList();
        for (AptSizeType type : types) {
            AptPriceDto.Simple row = AptPriceDtoMapper.INSTANCE.mapToSimple(type);
            row.setSizeNo(type.getId().getSizeNo());

            String name = "";

            if (kbId.startsWith("KBO")) {
                // 오피스텔
                name += ((int) Math.floor(type.getSize() / 3.3)) + "평 (전용 " + type.getOnlySize() + "m²)";
                if (StringUtils.isNotEmpty(type.getType().trim())) {
                    name += " - " + type.getType() + "타입";
                }
            } else {
                // 아파트
                name += type.getSize() + type.getType()
                        + "m² ("
                        + ((int) Math.floor(type.getSize() / 3.3))
                        + "평)";
            }

            row.setLabel(name);
            //row.setHouseCnt(type.getHomeCount());
            row.setHouseCnt(Integer.parseInt(String.valueOf(aptComplex.getTotalLive())));
            // 총세대수 추가
            //row.setTotGen(String.valueOf(aptComplex.getTotalLive()));
            AptPrice price = prices.stream()
                    .filter(v -> v.getId().getSizeNo().equals(type.getId().getSizeNo())).findFirst()
                    .orElse(null);

            if (price != null) {
                row.setUpperAvrDealPrc(price.getUpperAvrDealPrc());
                row.setNomAvrDealPrc(price.getNomAvrDealPrc());
                row.setSubAvrDealPrc(price.getSubAvrDealPrc());
            }

            items.add(row);
        }

        return items;
    }

    public List<OfficialItemDong> getOfficial(String mid) {
        String bcode = mid.substring(0, 10);
        List<Map<String, Object>> o = officialLandRepository.getObject(bcode, mid);

        List<Official> objects = o.stream()
                .map(v -> mapper.map(v, Official.class))
                .collect(Collectors.toList());

        Map<String, List<OfficialItemHo>> output = new LinkedHashMap<>();

        objects.stream().forEach(v -> {
            String key = v.getDongCode() + "@@" + v.getDong();
            if (!output.containsKey(key)) {
                output.put(key, Lists.newArrayList());
            }

            OfficialItemHo ho = new OfficialItemHo();
            ho.setCode(v.getHoCode());
            ho.setLabel(v.getHo());
            ho.setOfficialPrice(v.getOfficialPrice() / 10000);
            ho.setAreaSize(v.getAreaSize());

            output.get(key).add(ho);
        });

        return output.keySet().stream().map(v -> {
            String[] keys = v.split("@@");

            OfficialItemDong dong = new OfficialItemDong();
            dong.setCode(keys[0]);
            dong.setLabel(keys.length == 1 || StringUtils.isEmpty(keys[1]) ? "_default_" : keys[1]);
            dong.setItems(output.get(v));
            return dong;
        }).collect(Collectors.toList());

    }


    /*
        데이터 기준 설정 - KB 시세 Service
     */
    public PagableDto.Response<AptComplexDto.KbResponse> getKbPage(AptComplexDto.Request request) {
        Restrictions r = new Restrictions(Conn.OR);
        if (request.getSearchText() != null) {
            String search = request.getSearchText().trim();
            search = search.replaceAll("\\s+", "%");
            r.like("searchText", "%" + search + "%");
            r.like("searchTextRoad", "%" + search + "%");
            r.like("name", "%" + search + "%");
        }

        Sort sort = Sort.by(Sort.Order.desc("updatedOn"));
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit(), sort);
        Page<AptComplex> page = aptComplexRepository.findAll(r.output(), pageRequest);

        List<AptComplexDto.KbResponse> cmsResponseList = page.getContent()
                .stream()
                .map(AptComplex::toKbResponse)
                .collect(Collectors.toList());

        PagableDto.Response<AptComplexDto.KbResponse> pages = PagableDto.Response.of(page,
                cmsResponseList);
//
//
//        PagableDto.Response<AptComplexDto.KbResponse> result = new PagableDto.Response<>();
//        result.setTotalElements(page.getTotalElements());
//        result.setNumber(page.getNumber());
//        result.setSize(page.getSize());
//        result.setContent(
//                page.getContent()
//                        .stream()
//                        .map(v -> AptComplexDtoMapper.INSTANCE.toKbResponse(v))
//                        .collect(Collectors.toList())
//        );
        return pages;
    }

    @Transactional(readOnly = false)
    public long createByCsv(AptSales.Type type, MultipartFile file) throws Exception {
        ByteArrayInputStream stream = new ByteArrayInputStream(file.getInputStream().readAllBytes());
        CSVReader reader = new CSVReader(new InputStreamReader(stream, "EUC-KR"));

        List<String[]> rows = reader.readAll();

        boolean success = true;
        List<AptSales> sales = Lists.newArrayList();
        for (int i = 16; i < rows.size(); i++) {
            try {
                String[] row = rows.get(i);

                AptSales aptSales = new AptSales();
                aptSales.setType(type);
                aptSales.setSigungu(row[0]);
                aptSales.setBunji(row[1]);
                aptSales.setMainNo(row[2]);
                aptSales.setSubNo(row[3]);
                aptSales.setName(row[4]);
                aptSales.setOnlySize(Double.parseDouble(row[5].replaceAll("[^0-9\\.]+", "")));

                String dt = row[6];
                if (row[7].length() == 1) {
                    dt += "0";
                }
                dt += row[7];
                LocalDate saledAt = LocalDate.parse(dt, DateTimeFormatter.ofPattern("yyyyMMdd"));
                aptSales.setSaledAt(saledAt);
                if (StringUtils.isNotEmpty(row[8].replaceAll("[^0-9]", ""))) {
                    aptSales.setPrice(Long.parseLong(row[8].replaceAll("[^0-9]", "")));
                }
                if (StringUtils.isNotEmpty(row[9].replaceAll("[^0-9]", ""))) {
                    aptSales.setFloor(Integer.parseInt(row[9].replaceAll("[^0-9]", "")));
                }
                if (StringUtils.isNotEmpty(row[10].replaceAll("[^0-9]", ""))) {
                    aptSales.setBuildYear(Integer.parseInt(row[10].replaceAll("[^0-9]", "")));
                }
                aptSales.setRoad(row[11]);
                aptSales.setFailure(row[12]);
                aptSales.setSaleType(row[13]);
                aptSales.setManager(row[14]);
                sales.add(aptSales);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(i);
                System.out.println(rows.get(i));

                success = false;
                break;
            }
        }

        if (success) {
            aptSalesRepository.saveAll(sales);
        }
        return rows.size() - 16;
    }


    @Transactional(readOnly = false)
    public void updateSales(Integer page) throws Exception {

        Restrictions r = new Restrictions();
//    r.ilike("kbCode", "15970");
        r.isNull("fullAddress");
//    r.ilike("searchTextRoad", "%동탄숲속로%");

        PageRequest req = PageRequest.of(1, 1000);

        Double mainProg = null;
        Page<AptComplex> aptComplexes = aptComplexRepository.findAll(r.output(), req);
        for (AptComplex aptComplex : aptComplexes) {

            List<String> addrs = Lists.newArrayList();
            addrs.add(aptComplex.getAddr1().trim());
            addrs.add(aptComplex.getAddr2().trim());
            addrs.add(aptComplex.getAddr3().trim());
            addrs.add(aptComplex.getAddr4().trim());
            addrs.add(aptComplex.getAddr5().trim());
            addrs.add(aptComplex.getAddr6().trim());
//      addrs.add(aptComplex.getAddr7().trim());

            String originAddress = StringUtils.join(
                    addrs.stream().filter(v -> StringUtils.isNotEmpty(v)).collect(Collectors.toList()),
                    " "
            );

            List<String> addrs2 = Lists.newArrayList();
            addrs2.add(aptComplex.getAddr1().trim());
            addrs2.add(aptComplex.getAddr2().trim());
            addrs2.add(aptComplex.getAddr3().trim());
            addrs2.add(aptComplex.getAddr4().trim());
            addrs2.add(aptComplex.getName());

            String withName = StringUtils.join(
                    addrs2.stream().filter(v -> StringUtils.isNotEmpty(v)).collect(Collectors.toList()),
                    " "
            );

            List<String> queries = Lists.newArrayList();
            queries.add(originAddress);
            if (StringUtils.isNotEmpty(aptComplex.getAddr7().trim())) {
                queries.add(originAddress + " " + aptComplex.getAddr7());
            }
            queries.add(aptComplex.getRoadAddress() + " " + aptComplex.getRoadAddressDetails());
            queries.add(withName);
            queries.add(aptComplex.getAddr1() + " " + aptComplex.getAddr2() + " " + aptComplex.getName());
            queries.add(aptComplex.getName().trim());

            Restrictions r0 = new Restrictions();
            r0.ilike("searchText",
                    "%" + aptComplex.getAddr1() + "%" + aptComplex.getAddr2() + "%" + aptComplex.getName()
                            + "%");
            List<Postcode> postcodes = postcodeRepository.findAll(r0.output());
            System.out.println(postcodes);

            JSONObject address = null;

            for (String query : queries) {
                if (StringUtils.isEmpty(query.trim())) {
                    continue;
                }
                HttpHeaders headers = new HttpHeaders();
                headers.add("X-NCP-APIGW-API-KEY-ID", "ogj599drac");
                headers.add("X-NCP-APIGW-API-KEY", "NPR6csUQysH5fatkrUnIMeiko8yvaAfSXcPcHoNc");

                HttpEntity entity = new HttpEntity(headers);
                ResponseEntity<JSONObject> object = restTemplate.exchange(
                        "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query={q}",
                        HttpMethod.GET,
                        entity,
                        JSONObject.class,
                        query
                );

                if (!object.getBody().containsKey("addresses")) {
                    System.out.println(object.getBody().toString(

                    ));
                    continue;
                }

                boolean isOnlyName = aptComplex.getName().trim().equals(query);

                JSONArray addresses = object.getBody().getJSONArray("addresses");
                if (isOnlyName) {
                    List<JSONObject> objects = Lists.newArrayList();
                    for (int i = 0; i < addresses.size(); i++) {
                        String jibun = addresses.getJSONObject(i).getString("jibunAddress");
                        String prefix = aptComplex.getAddr1().trim() + " " + aptComplex.getAddr2().trim();
                        if (jibun.startsWith(prefix)) {
                            objects.add(addresses.getJSONObject(i));
                        }
                    }
                    if (objects.size() != 1) {
                        System.out.println(object.getBody().toString());
                        continue;
                    }
                    address = objects.get(0);
                } else {
                    if (addresses.size() != 1) {
                        System.out.println(object.getBody().toString());
                        continue;
                    }
                    address = addresses.getJSONObject(0);
                }
            }

            if (address == null) {
                System.out.println("----------------------");
                System.out.println("----------------------");
                System.out.println("Not Found Address...");
                for (String query : queries) {
                    if (StringUtils.isEmpty(query.trim())) {
                        continue;
                    }
                    System.out.println(query);
                }
                System.out.println("----------------------");
                System.out.println("----------------------");
                continue;
//        throw new Exception("Not Found Address...");
            }
            Double x = Double.parseDouble(address.getString("x"));
            Double y = Double.parseDouble(address.getString("y"));

            if (x != null) {
                aptComplex.setLng(address.getDouble("x"));
            }

            if (y != null) {
                aptComplex.setLat(address.getDouble("y"));
            }

            aptComplex.setFullAddress(address.getString("jibunAddress"));

            {
                String sido = "";
                String dongmyun = "";
                String roadName = "";
                String buildName = "";
                JSONArray els = address.getJSONArray("addressElements");
                for (int i = 0; i < els.size(); i++) {
                    JSONObject row = els.getJSONObject(i);
                    if ("ROAD_NAME".equals(row.getJSONArray("types").getString(0))) {
                        roadName = row.getString("longName").trim();
                    }
                    if ("BUILDING_NAME".equals(row.getJSONArray("types").getString(0))) {
                        buildName = row.getString("longName").trim();
                    }
                    if ("SIDO".equals(row.getJSONArray("types").getString(0))) {
                        sido = row.getString("longName").trim();
                    }
                    if ("DONGMYUN".equals(row.getJSONArray("types").getString(0))) {
                        dongmyun = row.getString("longName").trim();
                    }
                }

                if (StringUtils.isNotEmpty(sido)) {
                    aptComplex.setAddr1(sido);
                }

                if (StringUtils.isNotEmpty(roadName)) {
                    String ra = address.getString("roadAddress");

                    String raPrefix = ra.substring(0, ra.indexOf(roadName) + roadName.length()).trim();
                    String raSuffix = ra.substring(ra.indexOf(roadName) + roadName.length()).trim();
                    if (StringUtils.isNotEmpty(buildName)) {
                        raSuffix = raSuffix.replaceAll(buildName, "");
                    }

                    if (StringUtils.isNotEmpty(dongmyun) && dongmyun.endsWith("동")) {
                        raSuffix = raSuffix.trim() + " (" + dongmyun + ")";
                    }

//          System.out.println("raPrefix :" + raPrefix);
//          System.out.println("raSuffix :" + raSuffix);

                    aptComplex.setRoadAddress(raPrefix);
                    aptComplex.setRoadAddressDetails(raSuffix);

//          System.out.println(aptComplex.getRoadAddress());
//          System.out.println(aptComplex.getRoadAddressDetails());

                }
            }
            //ROAD_NAME

            aptComplexRepository.save(aptComplex);

            Double progress = Math.floor(
                    aptComplexes.getContent().indexOf(aptComplex) * 100
                            / aptComplexes.getContent().size()
            );
            if (!progress.equals(mainProg)) {
                System.out.println("Progress..." + progress + "%");
                mainProg = progress;
            }
//      break;
        }
    }

    @Autowired
    private EntityManager entityManager;

    public List<String> updateSales2() throws Exception {

        List<String> bcodes = entityManager.createNativeQuery(
                "select bcode from area_code where length(bcode) = 5"
        ).getResultList();

        return bcodes;

    }

    @Transactional(readOnly = false)
    public void updateSales3(JSONObject row) {

        AptSales aptSales = new AptSales();
        aptSales.setType(Type.APT);
        aptSales.setMethod(row.getInt("월세금액") == 0 ? Method.LEASE : Method.LEASE_MONTH);
        aptSales.setSigungu(row.getString("지역코드"));
        aptSales.setBunji(row.getString("지번"));
//    aptSales.setMainNo(row[2]);
//    aptSales.setSubNo(row[3]);
        aptSales.setName(row.getString("아파트"));
        aptSales.setOnlySize(row.getDouble("전용면적"));

        String dt = row.getString("년");
        if (row.getString("월").length() == 1) {
            dt += "0";
        }
        dt += row.getString("월");
        if (row.getString("일").length() == 1) {
            dt += "0";
        }
        dt += row.getString("일");
        LocalDate saledAt = LocalDate.parse(dt, DateTimeFormatter.ofPattern("yyyyMMdd"));
        aptSales.setSaledAt(saledAt);

        {
            String m = row.getString("보증금액").replaceAll("[^0-9]", "");
            if (StringUtils.isNotEmpty(m)) {
                aptSales.setDeposit(Long.parseLong(m));
            }
        }

        aptSales.setBuildYear(row.getInt("건축년도"));
        aptSales.setRoad("!" + row.getString("법정동"));
        aptSalesRepository.save(aptSales);
    }


    @Transactional(readOnly = false)
    public void updateCachedSales(String kbId) {

        AptComplex aptComplex = aptComplexRepository.getById(kbId);

        String name = aptComplex.getName().trim();
        if (name.contains("(")) {
            String fx = name.substring(name.indexOf("(") + 1).trim();
            if (fx.contains(")")) {
                fx = fx.substring(0, fx.indexOf(")"));
            }
            name = fx + " " + name.substring(0, name.indexOf("("));
        }

        name = name.replaceAll("\\s+", "");
        name = name.replaceAll("|", "%");

        String suffix =
                aptComplex.getAddr1() + " " + aptComplex.getAddr2() + " " + aptComplex.getAddr3();
        suffix = suffix.replaceAll("\\s+", " ");

        String roadAddress =
                aptComplex.getRoadAddress() + " " + aptComplex.getRoadBuildMainNo().trim();
        if (!"0".equals(aptComplex.getRoadBuildSubNo().trim()) && StringUtils.isNotEmpty(
                aptComplex.getRoadBuildSubNo().trim())) {
            roadAddress += "-" + aptComplex.getRoadBuildSubNo();
        }

        roadAddress = roadAddress.replaceAll(suffix, "").trim();
        roadAddress = roadAddress.replaceAll("\\s+", " ");

        System.out.println(name);
        System.out.println(roadAddress);

        String mainNo = "%" + aptComplex.getAddr6().replaceAll("[^0-9]+", "");
        String subNo = "%" + aptComplex.getAddr7().replaceAll("[^0-9]+", "");

        String mainNo2 = "%0", subNo2 = "%0";
        if (aptComplex.getAddr7().contains("-")) {
            String[] addr7 = aptComplex.getAddr7().split("-");
            mainNo2 = "%" + addr7[0].replaceAll("[^0-9]+", "");
            subNo2 = "%" + addr7[1].replaceAll("[^0-9]+", "");
        }

//    System.out.println(mainNo);
//    System.out.println(subNo);

        String type = "APT";
        if (aptComplex.getId().startsWith("KBO")) {
            type = "OFT";
        }

        if ("%".equals(mainNo) || "%0".equals(mainNo)) {
            mainNo = "9999";
        }

        if ("%".equals(subNo) || "%0".equals(subNo)) {
            subNo = "9999";
        }

        if ("%".equals(mainNo2) || "%0".equals(mainNo2)) {
            mainNo2 = "9999";
        }

        if ("%".equals(subNo2) || "%0".equals(subNo2)) {
            subNo2 = "9999";
        }

        Long itemSize = aptSalesRepository.getNames(
                type,
                suffix.trim().replaceAll("\\s+", "%") + "%",
                name
        );

        String ra = "%" + roadAddress.trim().replaceAll("\\s+", "%");
        if (itemSize != null && itemSize.equals(1L)) {
            ra = "";
        } else {
            System.out.println("itemSize >> " + itemSize);
        }

        System.out.println("type " + type);
        System.out.println("suffix " + suffix.trim().replaceAll("\\s+", "%") + "%");
        System.out.println("name " + name);
        System.out.println("roadAddress " + ra);
        System.out.println("aptComplexid " + aptComplex.getId());

        aptSalesRepository.updateToComplex(
                type,
                suffix.trim().replaceAll("\\s+", "%") + "%",
                name,
                ra,
//        mainNo,
//        subNo,
//        mainNo2,
//        subNo2,
                aptComplex.getId()
        );

    }


}
