package com.sailotech.mcap.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "upload_file_details")
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Data
@EqualsAndHashCode(of = "id", callSuper = false)
public class UploadFileDetails implements Serializable {

	private static final long serialVersionUID = 5317209769244252832L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "file_name", nullable = true)
	private String fileName;

	@Column(name = "file_location", nullable = true)
	private String fileLocation;

	@Column(name = "status", nullable = true)
	private String status;

	@Column(name = "company_id", nullable = false)
	private Integer companyId;

	@Column(name = "uploaded_by")
	private Integer uploadedBy;

	@Column(name = "uploaded_on")
	private Date uploadedOn;

	@Column(name = "file_size")
	private Integer fileSize;

	@Column(name = "file_bytes", columnDefinition = "blob")
	private byte[] fileBytes;

	@Column(name = "reason_to_fail")
	private String reason;

}
