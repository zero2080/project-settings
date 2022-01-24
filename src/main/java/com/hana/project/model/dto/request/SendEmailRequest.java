package com.hana.project.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hana.project.model.type.EmailTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class SendEmailRequest {

  private final int templateSid;
  private final boolean individual = false;
  private final boolean advertising = false;
  private final Map<String, Object> parameters;
  private final List<Recipient> recipients = new ArrayList<>();

  private SendEmailRequest(EmailTemplate template, String receiverAddress,
      Map<String, Object> parameters) {
    this.templateSid = template.getId();
    this.parameters = parameters;
    this.recipients.add(new Recipient("R", null, receiverAddress));
  }

  public static SendEmailRequest of(EmailTemplate template, String receiverAddress,
      Map<String, Object> parameters) {
    return new SendEmailRequest(template, receiverAddress, parameters);
  }

  @Getter
  @AllArgsConstructor
  @JsonInclude(Include.NON_NULL)
  private static class Recipient {

    private String type;
    private String name;
    private String address;
  }
}