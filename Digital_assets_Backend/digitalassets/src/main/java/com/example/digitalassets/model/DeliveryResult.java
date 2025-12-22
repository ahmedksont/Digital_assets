package com.example.digitalassets.model;

public class DeliveryResult {
    public enum Status { SUCCESS, FORBIDDEN, NOT_FOUND, ERROR }

    private Status status;
    private byte[] pdfBytes;
    private String assetVersion;
    private String upsellId;
    private String upsellLink;
    private String message;

    public static DeliveryResult success(byte[] pdfBytes, String assetVersion, String upsellId, String upsellLink) {
        DeliveryResult r = new DeliveryResult();
        r.status = Status.SUCCESS;
        r.pdfBytes = pdfBytes;
        r.assetVersion = assetVersion;
        r.upsellId = upsellId;
        r.upsellLink = upsellLink;
        return r;
    }

    public static DeliveryResult forbidden(String msg) {
        DeliveryResult r = new DeliveryResult();
        r.status = Status.FORBIDDEN;
        r.message = msg;
        return r;
    }

    public static DeliveryResult notFound(String msg) {
        DeliveryResult r = new DeliveryResult();
        r.status = Status.NOT_FOUND;
        r.message = msg;
        return r;
    }

    public static DeliveryResult error(String msg) {
        DeliveryResult r = new DeliveryResult();
        r.status = Status.ERROR;
        r.message = msg;
        return r;
    }

    // getters
    public Status getStatus() { return status; }
    public byte[] getPdfBytes() { return pdfBytes; }
    public String getAssetVersion() { return assetVersion; }
    public String getUpsellId() { return upsellId; }
    public String getUpsellLink() { return upsellLink; }
    public String getMessage() { return message; }
}
