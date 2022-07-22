package com.template.api.utils;

import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ActUtils {

    private ActUtils() {
    }

    //region ==============================isEmpty==============================

    /**
     * 문자열 체크(빈 문자열 존재)
     *
     * @param obj 문자열
     * @return true/false
     */
    public static boolean isEmpty(String obj) {
        return StringUtils.isEmpty(obj);
    }

    /**
     * Collection 체크
     *
     * @param obj Collection
     * @return true/false
     */
    public static boolean isEmpty(Collection obj) {
        return CollectionUtils.isEmpty(obj);
    }

    /**
     * Map 체크
     *
     * @param obj Map
     * @return true/false
     */
    public static boolean isEmpty(Map obj) {
        return MapUtils.isEmpty(obj);
    }
    //endregion

    //region ==============================isNotBlank==============================

    /**
     * 객체 체크(빈 객체 존재)
     *
     * @param obj 객체
     * @return true/false
     */
    public static boolean isEmpty(Object obj) {
        return ObjectUtils.isEmpty(obj);
    }
    //endregion

    //region ==============================isNotEmpty==============================

    /**
     * 문자열 체크(빈 문자열 존재)
     *
     * @param obj 문자열
     * @return true/false
     */
    public static boolean isNotEmpty(String obj) {
        return StringUtils.isNotEmpty(obj);
    }

    /**
     * Collection 체크
     *
     * @param obj Collection
     * @return true/false
     */
    public static boolean isNotEmpty(Collection obj) {
        return CollectionUtils.isNotEmpty(obj);
    }

    /**
     * Map 체크
     *
     * @param obj Map
     * @return true/false
     */
    public static boolean isNotEmpty(Map obj) {
        return MapUtils.isNotEmpty(obj);
    }
    //endregion

    //region ==============================isBlank==============================

    /**
     * 문자열 체크(빈 문자열 미존재)
     *
     * @param obj 문자열
     * @return true/false
     */
    public static boolean isBlank(String obj) {
        return StringUtils.isBlank(obj);
    }
    //endregion

    //region ==============================isNotBlank==============================

    /**
     * 문자열 체크(빈 문자열 미존재)
     *
     * @param obj 문자열
     * @return true/false
     */
    public static boolean isNotBlank(String obj) {
        return StringUtils.isNotBlank(obj);
    }
    //endregion

    //region ==============================isNotBlank==============================

    /**
     * 객체 체크(빈 객체 미존재)
     *
     * @param obj 객체
     * @return true/false
     */
    public static boolean isNotEmpty(Object obj) {
        return ObjectUtils.isNotEmpty(obj);
    }
    //endregion

    //region ==============================UUID_v4==============================
    public static String getUUID_V4() {
        return UUID.randomUUID().toString();
    }
    //endregion

    //region ==============================패스워드 검증==============================

    /**
     * 패스워드 검증
     *
     * @param password     패스워드문자열
     * @param minLength    최소 자리수
     * @param maxLength    최대 자리수
     * @param isContinuous 3자리 이상 연속되는 문자/숫자 ex) 123,321, abc, cba
     * @param isSame       3자리 이상 같은 문자/숫자 ex) 111, aaa
     * @param isSpecialMix 특수문자 혼합 체크 여부 ex) 문자,숫자,특수문자 혼합 체크 (특수문자 범위 !,@,#,$,%,^,&,*,(,) )
     * @return Map Key => 결과값, Value => 메시지
     */
    public static boolean isPasswordValidation(String password,
                                               int minLength,
                                               int maxLength,
                                               boolean isContinuous,
                                               boolean isSame,
                                               boolean isSpecialMix) {

        // 정규식 객체
        Matcher matcher;

        //region 공백 체크

        //공백 체크
        if (isBlank(password)) {
            return false;
        }

        // 공백 정규식
        String blankReg = "(\\s)";

        //공백 정규식 객체 삽입
        matcher = Pattern.compile(blankReg).matcher(password);

        //공백 체크
        if (matcher.find()) {
            return false;
        }

        //endregion

        //패스워드 Upper
        String upperPwd = password.toUpperCase();

        //region 문자열 자리수 체크
        int pwdLength = upperPwd.length();

        if (minLength > maxLength)
            maxLength = minLength;

        if (pwdLength <= minLength || pwdLength >= maxLength) {
            return false;
        }
        //endregion

        //region 3자리 이상 연속되는 문자/숫자 체크

        if (isContinuous) {
            int[] tempArray = new int[pwdLength];

            //아스키코드 Array 삽입
            for (int i = 0; i < pwdLength; i++) {
                tempArray[i] = upperPwd.charAt(i);
            }

            //체크
            for (int i = 0; i < pwdLength - 2; i++) {
                //첫번째 문자가 0-9 또는 A-Z
                if ((tempArray[i] > 47 && tempArray[i + 2] < 58)
                        || (tempArray[i] > 64 && tempArray[i + 2] < 91)) {
                    // 배열의 연속된 수 검사
                    // 3번째 글자 - 2번째 글자 = 1, 3번째 글자 - 1번째 글자 = 2

                    //아스키코드로 3번째 문자 - 1번째 문자가 2이고 3번째 문자 - 2번째 문자가 1이면 연속된 숫자
                    if (Math.abs(tempArray[i + 2] - tempArray[i]) == 2
                            && Math.abs(tempArray[i + 2] - tempArray[i + 1]) == 1) {
                        return false;
                    }
                }
            }
        }

        //endregion

        //region 3자리 같은 문자/숫자 체크

        if (isSame) {
            // 3자리 같은 문자 정규식
            String sameReg = "(\\w)\\1\\1";

            matcher = Pattern.compile(sameReg).matcher(upperPwd);

            //체크
            if (matcher.find()) {
                return false;
            }
        }

        //endregion

        //region 특수문자 혼합 체크
        if (isSpecialMix) {
            // 영어, 숫자, 특수문자 포함한 정규식
            String pwdReg = "^((?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W]).{" + minLength + "," + maxLength + "})$";

            matcher = Pattern.compile(pwdReg).matcher(upperPwd);

            //체크
            if (!matcher.find()) {
                return false;
            }

        }
        //endregion

        return true;
    }

    /**
     * 패스워드 검증
     *
     * @param password     패스워드문자열
     * @param minLength    최소 자리수
     * @param maxLength    최대 자리수
     * @param isContinuous 3자리 이상 연속되는 문자/숫자 ex) 123,321, abc, cba
     * @param isSame       3자리 이상 같은 문자/숫자 ex) 111, aaa
     * @param isSpecialMix 특수문자 혼합 체크 여부 ex) 문자,숫자,특수문자 혼합 체크 (특수문자 범위 !,@,#,$,%,^,&,*,(,) )
     * @return
     */
    public static Map<String, String> passwordValidation(String password,
                                                         int minLength,
                                                         int maxLength,
                                                         boolean isContinuous,
                                                         boolean isSame,
                                                         boolean isSpecialMix) {

        Map<String, String> returnValue = Maps.newHashMap();

        // 정규식 객체
        Matcher matcher;

        //region 공백 체크

        //공백 체크
        if (isBlank(password)) {
            returnValue.put("status", "false");
            returnValue.put("message", "blank");
            return returnValue;
        }

        // 공백 정규식
        String blankReg = "(\\s)";

        //공백 정규식 객체 삽입
        matcher = Pattern.compile(blankReg).matcher(password);

        //공백 체크
        if (matcher.find()) {
            returnValue.put("status", "false");
            returnValue.put("message", "blank");
            return returnValue;
        }

        //endregion

        //패스워드 Upper
        String upperPwd = password.toUpperCase();

        //region 문자열 자리수 체크
        int pwdLength = upperPwd.length();

        if (minLength > maxLength)
            maxLength = minLength;

        if (pwdLength < minLength || pwdLength > maxLength) {
            returnValue.put("status", "false");
            returnValue.put("message", "length");
            return returnValue;
        }
        //endregion

        //region 3자리 이상 연속되는 문자/숫자 체크

        if (isContinuous) {
            int[] tempArray = new int[pwdLength];

            //아스키코드 Array 삽입
            for (int i = 0; i < pwdLength; i++) {
                tempArray[i] = upperPwd.charAt(i);
            }

            //체크
            for (int i = 0; i < pwdLength - 2; i++) {
                //첫번째 문자가 0-9 또는 A-Z
                if ((tempArray[i] > 47 && tempArray[i + 2] < 58)
                        || (tempArray[i] > 64 && tempArray[i + 2] < 91)) {
                    // 배열의 연속된 수 검사
                    // 3번째 글자 - 2번째 글자 = 1, 3번째 글자 - 1번째 글자 = 2

                    //아스키코드로 3번째 문자 - 1번째 문자가 2이고 3번째 문자 - 2번째 문자가 1이면 연속된 숫자
                    if (Math.abs(tempArray[i + 2] - tempArray[i]) == 2
                            && Math.abs(tempArray[i + 2] - tempArray[i + 1]) == 1) {
                        returnValue.put("status", "false");
                        returnValue.put("message", "continuous");
                        return returnValue;
                    }
                }
            }
        }

        //endregion

        //region 3자리 같은 문자/숫자 체크

        if (isSame) {
            // 3자리 같은 문자 정규식
            String sameReg = "(\\w)\\1\\1";

            matcher = Pattern.compile(sameReg).matcher(upperPwd);

            //체크
            if (matcher.find()) {
                returnValue.put("status", "false");
                returnValue.put("message", "same");
                return returnValue;
            }
        }

        //endregion

        //region 특수문자 혼합 체크
        if (isSpecialMix) {
            // 영어, 숫자, 특수문자 포함한 정규식
            String pwdReg = "^((?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W]).{" + minLength + "," + maxLength + "})$";

            matcher = Pattern.compile(pwdReg).matcher(upperPwd);

            //체크
            if (!matcher.find()) {
                returnValue.put("status", "false");
                returnValue.put("message", "specialMix");
                return returnValue;
            }

        }
        //endregion

        returnValue.put("status", "true");
        returnValue.put("message", "success");
        return returnValue;
    }
    //endregion

    //중복 제거를 위한 함수
    public static <T> Predicate<T> distinctByKey(Function<? super T,Object> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @SafeVarargs
    public static <T> Predicate<T> distinctByKey(final Function<? super T, ?>... keyExtractors)
    {
        final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

        return t ->
        {
            final List<?> keys = Arrays.stream(keyExtractors)
                    .map(ke -> ke.apply(t))
                    .collect(Collectors.toList());

            return seen.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }

}
