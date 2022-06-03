package com.template.api.jpa.converters;

import java.util.List;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ListAttributeConverter extends ObjectAttributeConverter<List> {

  @Override
  protected Class<List> getInstance() {
    return List.class;
  }

}
