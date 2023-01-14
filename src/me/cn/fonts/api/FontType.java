package me.cn.fonts.api;

public enum FontType {

    DM("diramight.ttf"), FIXEDSYS("tahoma.ttf"), ICONFONT("stylesicons.ttf"), FluxICONFONT("flux.ttf"), Check("check.ttf"), SF("SF.ttf"), SFBOLD("SFBOLD.ttf"), CHINESE("black.ttf"), Tahoma("Tahoma.ttf"), TahomaBold("Tahoma-Bold.ttf"), SFTHIN("SFREGULAR.ttf"), OXIDE("oxide.ttf"), Museo("MuseoSans_900.ttf"), Eaves("Eaves.ttf"), csgoicon("icomoon.ttf"), NeverLoserf("neverlose500.ttf");

    private final String fileName;

    private FontType(String fileName) {
        this.fileName = fileName;
    }

    public String fileName() {
        return this.fileName;
    }
}
