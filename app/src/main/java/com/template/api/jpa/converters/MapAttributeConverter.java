package com.template.api.jpa.converters;

import java.util.Map;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MapAttributeConverter extends ObjectAttributeConverter<Map> {

  @Override
  protected Class<Map> getInstance() {
    return Map.class;
  }

}
