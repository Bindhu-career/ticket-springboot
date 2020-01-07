package com.bigbang.api.models;

public class BillingInfo {
private String uuid;
private String userId;
private String projectId;
private String companyName;
private String fullName;
private String countryName;
private String address;
private String city;
private String zipCode;
private String vat;
private boolean invoices;
private String customerId;
private String cardBrand;
private String cardType;
private String cardLast4Digit;

public String getZipCode() {
	return zipCode;
}
public void setZipCode(String zipCode) {
	this.zipCode = zipCode;
}

public String getUuid() {
return uuid;
}
public void setUuid(String uuid) {
this.uuid = uuid;
}
public String getProjectId() {
return projectId;
}
public void setProjectId(String projectId) {
this.projectId = projectId;
}
public String getCompanyName() {
return companyName;
}
public void setCompanyName(String companyName) {
this.companyName = companyName;
}
public String getFullName() {
return fullName;
}
public void setFullName(String fullName) {
this.fullName = fullName;
}
public String getCountryName() {
return countryName;
}
public void setCountryName(String countryName) {
this.countryName = countryName;
}
public String getAddress() {
return address;
}
public void setAddress(String address) {
this.address = address;
}
public String getCity() {
return city;
}
public void setCity(String city) {
this.city = city;
}
public String getVat() {
return vat;
}
public void setVat(String vat) {
this.vat = vat;
}
public String getUserId() {
return userId;
}
public void setUserId(String userId) {
this.userId = userId;
}
public boolean isInvoices() {
return invoices;
}
public void setInvoices(boolean invoices) {
this.invoices = invoices;
}
public String getCustomerId() {
	return customerId;
}
public void setCustomerId(String customerId) {
	this.customerId = customerId;
}
public String getCardBrand() {
	return cardBrand;
}
public void setCardBrand(String cardBrand) {
	this.cardBrand = cardBrand;
}
public String getCardType() {
	return cardType;
}
public void setCardType(String cardType) {
	this.cardType = cardType;
}
public String getCardLast4Digit() {
	return cardLast4Digit;
}
public void setCardLast4Digit(String cardLast4Digit) {
	this.cardLast4Digit = cardLast4Digit;
}
@Override
public String toString() {
	return "BillingInfo [uuid=" + uuid + ", userId=" + userId + ", projectId=" + projectId + ", companyName="
			+ companyName + ", fullName=" + fullName + ", countryName=" + countryName + ", address=" + address
			+ ", city=" + city + ", zipCode=" + zipCode + ", vat=" + vat + ", invoices=" + invoices + ", customerId="
			+ customerId + ", cardBrand=" + cardBrand + ", cardType=" + cardType + ", cardLast4Digit=" + cardLast4Digit
			+ "]";
}




}
