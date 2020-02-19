package com.sailotech.mcap.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Data
@EqualsAndHashCode(of = "id", callSuper = false)
public class UploadFileDetailsDto implements Serializable {

	private static final long serialVersionUID = 5317209769244252832L;

	private Integer id;

	private String fileName;

	private String fileLocation;

	private String status;

	private Integer companyId;

	private Integer uploadedBy;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date uploadedOn;

	private Integer fileSize;

	private byte[] fileBytes;

	private String reason;

}
