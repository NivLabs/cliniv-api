package br.com.nivlabs.cliniv.models.dto;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Redes sociais")
public class SocialDTO extends DataTransferObjectBase {
    private String whatsApp;
    private String website;
    private String instagram;
    private String facebook;
    private String x;

    public SocialDTO() {
    }

    public SocialDTO(String whatsApp, String website, String instagram, String facebook, String x) {
        this.whatsApp = whatsApp;
        this.website = website;
        this.instagram = instagram;
        this.facebook = facebook;
        this.x = x;
    }

    public String getWhatsApp() {
        return whatsApp;
    }

    public void setWhatsApp(String whatsApp) {
        this.whatsApp = whatsApp;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "SocialDTO{" +
                "whatsApp='" + whatsApp + '\'' +
                ", website='" + website + '\'' +
                ", instagram='" + instagram + '\'' +
                ", facebook='" + facebook + '\'' +
                ", x='" + x + '\'' +
                '}';
    }
}
