package com.sampleapp.model;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "merchant", uniqueConstraints = {       
        @UniqueConstraint(columnNames = {
            "gst_no"
        })
})
@DynamicInsert
@DynamicUpdate
public class Merchant {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 
	 
	 @Size(max = 40)
	 private String contact_name;
	 
	 
	 @Size(max = 40)
	 @Pattern(regexp="^[0-9]{10}",message="Invalid phone number")
	 private String contact_number;
	 
//	 @NotBlank
	 @Size(max = 40)
	 @Pattern(regexp="[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
	            +"[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
	            +"(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?",
	                 message="Invalid Email. Please provide a valid mail id")
	 private String contact_email;
	 
	 @NotBlank
	 @Size(max = 300)
	 private String merchant_address1;
	 
	
	 @Size(max = 300)
	 private String merchant_address2;
	 
	 @NotBlank
	 @Size(max = 40)
	 private String merchant_city;
	 
	 @NotBlank
	 @Size(max = 40)
	 private String merchant_state;
	 
	 @NotBlank
	 @Size(max = 6)
	 private String merchant_zipcode;
	 
	 @NotBlank
	 @Size(max = 40)
	 private String merchant_country;
	 
	 @NotBlank
	 @Size(max = 40)
	 private String ifsc_code;
	 
	 @NotBlank
	 @Size(max = 40)
	 private String bank_name;
	 
	 @NotBlank
	 @Size(max = 40)
	 private String branch_name;

	    @NotBlank
	    @Size(max = 40)
	    private String company_name;

	    @NotBlank
	    @Size(max = 15)
	    private String company_logo;

	    
	    @NotBlank
	    @Size(max = 40)    
	    private String account_no;

	    @NotBlank
	    @Size(max = 15)
	    @Pattern(regexp="[0-9]{2}[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9A-Za-z]{1}[Z]{1}[0-9a-zA-Z]{1}",
	                 message="Invalid GST number. Enter the number in format '11AAAAA1111Q1ZB'")   
	    private String gst_no;
	    
	    	   
	    private Boolean isActive;

	    @OneToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

	    public Merchant() {
			// TODO Auto-generated constructor stub
		}

		public Merchant(Long id, @Size(max = 40) String contact_name, @Size(max = 40) String contact_number,
				@Size(max = 40) String contact_email, @NotBlank @Size(max = 40) String merchant_address1,
				@Size(max = 40) String merchant_address2, @NotBlank @Size(max = 40) String merchant_city,
				@NotBlank @Size(max = 40) String merchant_state, @NotBlank @Size(max = 6) String merchant_zipcode,
				@NotBlank @Size(max = 40) String merchant_country, @NotBlank @Size(max = 40) String ifsc_code,
				@NotBlank @Size(max = 40) String bank_name, @NotBlank @Size(max = 40) String branch_name,
				@NotBlank @Size(max = 40) String company_name, @NotBlank @Size(max = 15) String company_logo,
				@NotBlank @Size(max = 40) String account_no, @NotBlank @Size(max = 100) String gst_no,
				Boolean isActive, User user) {
			super();
			this.id = id;
			this.contact_name = contact_name;
			this.contact_number = contact_number;
			this.contact_email = contact_email;
			this.merchant_address1 = merchant_address1;
			this.merchant_address2 = merchant_address2;
			this.merchant_city = merchant_city;
			this.merchant_state = merchant_state;
			this.merchant_zipcode = merchant_zipcode;
			this.merchant_country = merchant_country;
			this.ifsc_code = ifsc_code;
			this.bank_name = bank_name;
			this.branch_name = branch_name;
			this.company_name = company_name;
			this.company_logo = company_logo;
			this.account_no = account_no;
			this.gst_no = gst_no;
			this.isActive = isActive;
			this.user = user;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getContact_name() {
			return contact_name;
		}

		public void setContact_name(String contact_name) {
			this.contact_name = contact_name;
		}

		public String getContact_number() {
			return contact_number;
		}

		public void setContact_number(String contact_number) {
			this.contact_number = contact_number;
		}

		public String getContact_email() {
			return contact_email;
		}

		public void setContact_email(String contact_email) {
			this.contact_email = contact_email;
		}

		public String getMerchant_address1() {
			return merchant_address1;
		}

		public void setMerchant_address1(String merchant_address1) {
			this.merchant_address1 = merchant_address1;
		}

		public String getMerchant_address2() {
			return merchant_address2;
		}

		public void setMerchant_address2(String merchant_address2) {
			this.merchant_address2 = merchant_address2;
		}

		public String getMerchant_city() {
			return merchant_city;
		}

		public void setMerchant_city(String merchant_city) {
			this.merchant_city = merchant_city;
		}

		public String getMerchant_state() {
			return merchant_state;
		}

		public void setMerchant_state(String merchant_state) {
			this.merchant_state = merchant_state;
		}

		public String getMerchant_zipcode() {
			return merchant_zipcode;
		}

		public void setMerchant_zipcode(String merchant_zipcode) {
			this.merchant_zipcode = merchant_zipcode;
		}

		public String getMerchant_country() {
			return merchant_country;
		}

		public void setMerchant_country(String merchant_country) {
			this.merchant_country = merchant_country;
		}

		public String getIfsc_code() {
			return ifsc_code;
		}

		public void setIfsc_code(String ifsc_code) {
			this.ifsc_code = ifsc_code;
		}

		public String getBank_name() {
			return bank_name;
		}

		public void setBank_name(String bank_name) {
			this.bank_name = bank_name;
		}

		public String getBranch_name() {
			return branch_name;
		}

		public void setBranch_name(String branch_name) {
			this.branch_name = branch_name;
		}

		public String getCompany_name() {
			return company_name;
		}

		public void setCompany_name(String company_name) {
			this.company_name = company_name;
		}

		public String getCompany_logo() {
			return company_logo;
		}

		public void setCompany_logo(String company_logo) {
			this.company_logo = company_logo;
		}

		public String getAccount_no() {
			return account_no;
		}

		public void setAccount_no(String account_no) {
			this.account_no = account_no;
		}

		public String getGst_no() {
			return gst_no;
		}

		public void setGst_no(String gst_no) {
			this.gst_no = gst_no;
		}

		

		public Boolean getIsActive() {
			return isActive;
		}

		public void setIsActive(Boolean isActive) {
			this.isActive = isActive;
		}

		@JsonIgnore
		public User getUser() {
			return user;
		}
		@JsonIgnore
		public void setUser(User user) {
			this.user = user;
		}

		@Override
		public String toString() {
			return "Merchant [id=" + id + ", contact_name=" + contact_name + ", contact_number=" + contact_number
					+ ", contact_email=" + contact_email + ", merchant_address1=" + merchant_address1
					+ ", merchant_address2=" + merchant_address2 + ", merchant_city=" + merchant_city
					+ ", merchant_state=" + merchant_state + ", merchant_zipcode=" + merchant_zipcode
					+ ", merchant_country=" + merchant_country + ", ifsc_code=" + ifsc_code + ", bank_name=" + bank_name
					+ ", branch_name=" + branch_name + ", company_name=" + company_name + ", company_logo="
					+ company_logo + ", account_no=" + account_no + ", gst_no=" + gst_no + ", isActive=" + isActive
					+ ", user=" + user + "]";
		}

		

		

}