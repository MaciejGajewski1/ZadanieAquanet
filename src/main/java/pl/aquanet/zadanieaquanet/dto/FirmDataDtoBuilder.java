package pl.aquanet.zadanieaquanet.dto;

public class FirmDataDtoBuilder {

    private FirmDataDto firmDataDto;

    public FirmDataDtoBuilder() {
        firmDataDto = new FirmDataDto();
    }

    public FirmDataDtoBuilder withFormaPrawna(String formaPrawna) {
        firmDataDto.setFormaPrawna(formaPrawna);
        return this;
    }

    public FirmDataDtoBuilder withRegon(String regon) {
        firmDataDto.setRegon(regon);
        return this;
    }

    public FirmDataDtoBuilder withNip(String nip) {
        firmDataDto.setNip(nip);
        return this;
    }

    public FirmDataDto build() {
        return firmDataDto;
    }
}
