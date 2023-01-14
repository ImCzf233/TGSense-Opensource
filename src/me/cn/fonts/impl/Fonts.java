package me.cn.fonts.impl;

import me.Skid.Client;
import me.cn.fonts.api.FontFamily;
import me.cn.fonts.api.FontManager;
import me.cn.fonts.api.FontRenderer;
import me.cn.fonts.api.FontType;

public interface Fonts {

    FontManager FONT_MANAGER = (FontManager) Client.getFontManager();

    public interface Tahoma {

        FontFamily Tahoma = Fonts.FONT_MANAGER.fontFamily(FontType.Tahoma);

        public static final class Tahoma_35 {

            public static final FontRenderer Tahoma_35 = Fonts.Tahoma.Tahoma.ofSize(35);
        }

        public static final class Tahoma_28 {

            public static final FontRenderer Tahoma_28 = Fonts.Tahoma.Tahoma.ofSize(28);
        }

        public static final class Tahoma_26 {

            public static final FontRenderer Tahoma_26 = Fonts.Tahoma.Tahoma.ofSize(26);
        }

        public static final class Tahoma_22 {

            public static final FontRenderer Tahoma_22 = Fonts.Tahoma.Tahoma.ofSize(22);
        }

        public static final class Tahoma_20 {

            public static final FontRenderer Tahoma_20 = Fonts.Tahoma.Tahoma.ofSize(20);
        }

        public static final class Tahoma_18 {

            public static final FontRenderer Tahoma_18 = Fonts.Tahoma.Tahoma.ofSize(18);
        }

        public static final class Tahoma_16 {

            public static final FontRenderer Tahoma_16 = Fonts.Tahoma.Tahoma.ofSize(16);
        }

        public static final class Tahoma_14 {

            public static final FontRenderer Tahoma_14 = Fonts.Tahoma.Tahoma.ofSize(14);
        }

        public static final class Tahoma_12 {

            public static final FontRenderer Tahoma_12 = Fonts.Tahoma.Tahoma.ofSize(12);
        }

        public static final class Tahoma_11 {

            public static final FontRenderer Tahoma_11 = Fonts.Tahoma.Tahoma.ofSize(11);
        }
    }

    public interface TahomaBold {

        FontFamily TahomaBold = Fonts.FONT_MANAGER.fontFamily(FontType.TahomaBold);

        public static final class TahomaBold_35 {

            public static final FontRenderer TahomaBold_35 = Fonts.TahomaBold.TahomaBold.ofSize(35);
        }

        public static final class TahomaBold_28 {

            public static final FontRenderer TahomaBold_28 = Fonts.TahomaBold.TahomaBold.ofSize(28);
        }

        public static final class TahomaBold_26 {

            public static final FontRenderer TahomaBold_26 = Fonts.TahomaBold.TahomaBold.ofSize(26);
        }

        public static final class TahomaBold_22 {

            public static final FontRenderer TahomaBold_22 = Fonts.TahomaBold.TahomaBold.ofSize(22);
        }

        public static final class TahomaBold_20 {

            public static final FontRenderer TahomaBold_20 = Fonts.TahomaBold.TahomaBold.ofSize(20);
        }

        public static final class TahomaBold_18 {

            public static final FontRenderer TahomaBold_18 = Fonts.TahomaBold.TahomaBold.ofSize(18);
        }

        public static final class TahomaBold_16 {

            public static final FontRenderer TahomaBold_16 = Fonts.TahomaBold.TahomaBold.ofSize(16);
        }

        public static final class TahomaBold_14 {

            public static final FontRenderer TahomaBold_14 = Fonts.TahomaBold.TahomaBold.ofSize(14);
        }

        public static final class TahomaBold_12 {

            public static final FontRenderer TahomaBold_12 = Fonts.TahomaBold.TahomaBold.ofSize(12);
        }

        public static final class TahomaBold_11 {

            public static final FontRenderer TahomaBold_11 = Fonts.TahomaBold.TahomaBold.ofSize(11);
        }
    }

    public interface SFBOLD {

        FontFamily SFBOLD = Fonts.FONT_MANAGER.fontFamily(FontType.SFBOLD);

        public static final class SFBOLD_35 {

            public static final FontRenderer SFBOLD_35 = Fonts.SFBOLD.SFBOLD.ofSize(35);
        }

        public static final class SFBOLD_28 {

            public static final FontRenderer SFBOLD_28 = Fonts.SFBOLD.SFBOLD.ofSize(28);
        }

        public static final class SFBOLD_22 {

            public static final FontRenderer SFBOLD_22 = Fonts.SFBOLD.SFBOLD.ofSize(22);
        }

        public static final class SFBOLD_20 {

            public static final FontRenderer SFBOLD_20 = Fonts.SFBOLD.SFBOLD.ofSize(20);
        }

        public static final class SFBOLD_19 {

            public static final FontRenderer SFBOLD_19 = Fonts.SFBOLD.SFBOLD.ofSize(19);
        }

        public static final class SFBOLD_18 {

            public static final FontRenderer SFBOLD_18 = Fonts.SFBOLD.SFBOLD.ofSize(18);
        }

        public static final class SFBOLD_17 {

            public static final FontRenderer SFBOLD_17 = Fonts.SFBOLD.SFBOLD.ofSize(17);
        }

        public static final class SFBOLD_16 {

            public static final FontRenderer SFBOLD_16 = Fonts.SFBOLD.SFBOLD.ofSize(16);
        }

        public static final class SFBOLD_14 {

            public static final FontRenderer SFBOLD_14 = Fonts.SFBOLD.SFBOLD.ofSize(14);
        }

        public static final class SFBOLD_15 {

            public static final FontRenderer SFBOLD_15 = Fonts.SFBOLD.SFBOLD.ofSize(15);
        }

        public static final class SFBOLD_13 {

            public static final FontRenderer SFBOLD_13 = Fonts.SFBOLD.SFBOLD.ofSize(13);
        }

        public static final class SFBOLD_12 {

            public static final FontRenderer SFBOLD_12 = Fonts.SFBOLD.SFBOLD.ofSize(12);
        }

        public static final class SFBOLD_11 {

            public static final FontRenderer SFBOLD_11 = Fonts.SFBOLD.SFBOLD.ofSize(11);
        }

        public static final class SFBOLD_10 {

            public static final FontRenderer SFBOLD_10 = Fonts.SFBOLD.SFBOLD.ofSize(10);
        }

        public static final class SFBOLD_25 {

            public static final FontRenderer SFBOLD_25 = Fonts.SFBOLD.SFBOLD.ofSize(25);
        }

        public static final class SFBOLD_26 {

            public static final FontRenderer SFBOLD_26 = Fonts.SFBOLD.SFBOLD.ofSize(26);
        }
    }

    public interface SFTHIN {

        FontFamily SFTHIN = Fonts.FONT_MANAGER.fontFamily(FontType.SFTHIN);

        public static final class SFTHIN_28 {

            public static final FontRenderer SFTHIN_28 = Fonts.SFTHIN.SFTHIN.ofSize(28);
        }

        public static final class SFTHIN_20 {

            public static final FontRenderer SFTHIN_20 = Fonts.SFTHIN.SFTHIN.ofSize(20);
        }

        public static final class SFTHIN_19 {

            public static final FontRenderer SFTHIN_19 = Fonts.SFTHIN.SFTHIN.ofSize(19);
        }

        public static final class SFTHIN_18 {

            public static final FontRenderer SFTHIN_18 = Fonts.SFTHIN.SFTHIN.ofSize(18);
        }

        public static final class SFTHIN_17 {

            public static final FontRenderer SFTHIN_17 = Fonts.SFTHIN.SFTHIN.ofSize(17);
        }

        public static final class SFTHIN_16 {

            public static final FontRenderer SFTHIN_16 = Fonts.SFTHIN.SFTHIN.ofSize(16);
        }

        public static final class SFTHIN_12 {

            public static final FontRenderer SFTHIN_12 = Fonts.SFTHIN.SFTHIN.ofSize(12);
        }

        public static final class SFTHIN_10 {

            public static final FontRenderer SFTHIN_10 = Fonts.SFTHIN.SFTHIN.ofSize(10);
        }
    }

    public interface CHINESE {

        FontFamily CHINESE = Fonts.FONT_MANAGER.fontFamily(FontType.CHINESE);

        public static final class CHINESE_50 {

            public static final FontRenderer CHINESE_50 = Fonts.CHINESE.CHINESE.ofSize(45);
        }

        public static final class CHINESE_31 {

            public static final FontRenderer CHINESE_31 = Fonts.CHINESE.CHINESE.ofSize(31);
        }

        public static final class CHINESE_30 {

            public static final FontRenderer CHINESE_30 = Fonts.CHINESE.CHINESE.ofSize(30);
        }

        public static final class CHINESE_29 {

            public static final FontRenderer CHINESE_29 = Fonts.CHINESE.CHINESE.ofSize(29);
        }

        public static final class CHINESE_28 {

            public static final FontRenderer CHINESE_28 = Fonts.CHINESE.CHINESE.ofSize(28);
        }

        public static final class CHINESE_27 {

            public static final FontRenderer CHINESE_27 = Fonts.CHINESE.CHINESE.ofSize(27);
        }

        public static final class CHINESE_26 {

            public static final FontRenderer CHINESE_26 = Fonts.CHINESE.CHINESE.ofSize(26);
        }

        public static final class CHINESE_25 {

            public static final FontRenderer CHINESE_25 = Fonts.CHINESE.CHINESE.ofSize(25);
        }

        public static final class CHINESE_24 {

            public static final FontRenderer CHINESE_24 = Fonts.CHINESE.CHINESE.ofSize(24);
        }

        public static final class CHINESE_23 {

            public static final FontRenderer CHINESE_23 = Fonts.CHINESE.CHINESE.ofSize(23);
        }

        public static final class CHINESE_22 {

            public static final FontRenderer CHINESE_22 = Fonts.CHINESE.CHINESE.ofSize(22);
        }

        public static final class CHINESE_21 {

            public static final FontRenderer CHINESE_21 = Fonts.CHINESE.CHINESE.ofSize(21);
        }

        public static final class CHINESE_20 {

            public static final FontRenderer CHINESE_20 = Fonts.CHINESE.CHINESE.ofSize(20);
        }

        public static final class CHINESE_19 {

            public static final FontRenderer CHINESE_19 = Fonts.CHINESE.CHINESE.ofSize(19);
        }

        public static final class CHINESE_18 {

            public static final FontRenderer CHINESE_18 = Fonts.CHINESE.CHINESE.ofSize(18);
        }

        public static final class CHINESE_17 {

            public static final FontRenderer CHINESE_17 = Fonts.CHINESE.CHINESE.ofSize(17);
        }

        public static final class CHINESE_16 {

            public static final FontRenderer CHINESE_16 = Fonts.CHINESE.CHINESE.ofSize(16);
        }

        public static final class CHINESE_15 {

            public static final FontRenderer CHINESE_15 = Fonts.CHINESE.CHINESE.ofSize(15);
        }

        public static final class CHINESE_14 {

            public static final FontRenderer CHINESE_14 = Fonts.CHINESE.CHINESE.ofSize(14);
        }
    }

    public interface Neverloerf {

        FontFamily NeverLoserf = Fonts.FONT_MANAGER.fontFamily(FontType.NeverLoserf);

        public static final class NLF_16 {

            public static final FontRenderer NLF_16 = Fonts.Neverloerf.NeverLoserf.ofSize(16);
        }

        public static final class NLF_15 {

            public static final FontRenderer NLF_15 = Fonts.Neverloerf.NeverLoserf.ofSize(15);
        }
    }

    public interface Eaves {

        FontFamily Eaves = Fonts.FONT_MANAGER.fontFamily(FontType.Eaves);

        public static final class Eaves_18 {

            public static final FontRenderer Eaves_18 = Fonts.Eaves.Eaves.ofSize(18);
        }
    }

    public interface Museo {

        FontFamily Museo = Fonts.FONT_MANAGER.fontFamily(FontType.Museo);

        public static final class Museo_20 {

            public static final FontRenderer Museo_20 = Fonts.Museo.Museo.ofSize(20);
        }
    }

    public interface SF {

        FontFamily SF = Fonts.FONT_MANAGER.fontFamily(FontType.SF);

        public static final class SF_50 {

            public static final FontRenderer SF_50 = Fonts.SF.SF.ofSize(45);
        }

        public static final class SF_31 {

            public static final FontRenderer SF_31 = Fonts.SF.SF.ofSize(31);
        }

        public static final class SF_30 {

            public static final FontRenderer SF_30 = Fonts.SF.SF.ofSize(30);
        }

        public static final class SF_29 {

            public static final FontRenderer SF_29 = Fonts.SF.SF.ofSize(29);
        }

        public static final class SF_28 {

            public static final FontRenderer SF_28 = Fonts.SF.SF.ofSize(28);
        }

        public static final class SF_27 {

            public static final FontRenderer SF_27 = Fonts.SF.SF.ofSize(27);
        }

        public static final class SF_26 {

            public static final FontRenderer SF_26 = Fonts.SF.SF.ofSize(26);
        }

        public static final class SF_25 {

            public static final FontRenderer SF_25 = Fonts.SF.SF.ofSize(25);
        }

        public static final class SF_24 {

            public static final FontRenderer SF_24 = Fonts.SF.SF.ofSize(24);
        }

        public static final class SF_23 {

            public static final FontRenderer SF_23 = Fonts.SF.SF.ofSize(23);
        }

        public static final class SF_22 {

            public static final FontRenderer SF_22 = Fonts.SF.SF.ofSize(22);
        }

        public static final class SF_21 {

            public static final FontRenderer SF_21 = Fonts.SF.SF.ofSize(21);
        }

        public static final class SF_20 {

            public static final FontRenderer SF_20 = Fonts.SF.SF.ofSize(20);
        }

        public static final class SF_19 {

            public static final FontRenderer SF_19 = Fonts.SF.SF.ofSize(19);
        }

        public static final class SF_18 {

            public static final FontRenderer SF_18 = Fonts.SF.SF.ofSize(18);
        }

        public static final class SF_17 {

            public static final FontRenderer SF_17 = Fonts.SF.SF.ofSize(17);
        }

        public static final class SF_16 {

            public static final FontRenderer SF_16 = Fonts.SF.SF.ofSize(16);
        }

        public static final class SF_15 {

            public static final FontRenderer SF_15 = Fonts.SF.SF.ofSize(15);
        }

        public static final class SF_14 {

            public static final FontRenderer SF_14 = Fonts.SF.SF.ofSize(14);
        }

        public static final class SF_11 {

            public static final FontRenderer SF_11 = Fonts.SF.SF.ofSize(11);
        }

        public static final class SF_9 {

            public static final FontRenderer SF_9 = Fonts.SF.SF.ofSize(9);
        }
    }

    public interface CsgoIcon {

        FontFamily csgoicon = Fonts.FONT_MANAGER.fontFamily(FontType.csgoicon);

        public static final class csgoicon_55 {

            public static final FontRenderer csgoicon_55 = Fonts.CsgoIcon.csgoicon.ofSize(55);
        }

        public static final class csgoicon_40 {

            public static final FontRenderer csgoicon_40 = Fonts.CsgoIcon.csgoicon.ofSize(40);
        }

        public static final class csgoicon_35 {

            public static final FontRenderer csgoicon_35 = Fonts.CsgoIcon.csgoicon.ofSize(35);
        }

        public static final class csgoicon_32 {

            public static final FontRenderer csgoicon_32 = Fonts.CsgoIcon.csgoicon.ofSize(32);
        }

        public static final class csgoicon_24 {

            public static final FontRenderer csgoicon_24 = Fonts.CsgoIcon.csgoicon.ofSize(24);
        }

        public static final class csgoicon_20 {

            public static final FontRenderer csgoicon_20 = Fonts.CsgoIcon.csgoicon.ofSize(20);
        }
    }

    public interface CheckFont {

        FontFamily CheckFont = Fonts.FONT_MANAGER.fontFamily(FontType.Check);

        public static final class CheckFont_50 {

            public static final FontRenderer CheckFont_50 = Fonts.CheckFont.CheckFont.ofSize(50);
        }

        public static final class CheckFont_35 {

            public static final FontRenderer CheckFont_35 = Fonts.CheckFont.CheckFont.ofSize(35);
        }

        public static final class CheckFont_32 {

            public static final FontRenderer CheckFont_32 = Fonts.CheckFont.CheckFont.ofSize(32);
        }

        public static final class CheckFont_24 {

            public static final FontRenderer CheckFont_24 = Fonts.CheckFont.CheckFont.ofSize(24);
        }

        public static final class CheckFont_20 {

            public static final FontRenderer CheckFont_20 = Fonts.CheckFont.CheckFont.ofSize(20);
        }

        public static final class CheckFont_18 {

            public static final FontRenderer CheckFont_18 = Fonts.CheckFont.CheckFont.ofSize(18);
        }

        public static final class CheckFont_17 {

            public static final FontRenderer CheckFont_17 = Fonts.CheckFont.CheckFont.ofSize(17);
        }

        public static final class CheckFont_16 {

            public static final FontRenderer CheckFont_16 = Fonts.CheckFont.CheckFont.ofSize(16);
        }
    }

    public interface FluxICONFONT {

        FontFamily FluxICONFONT = Fonts.FONT_MANAGER.fontFamily(FontType.FluxICONFONT);

        public static final class FluxICONFONT_18 {

            public static final FontRenderer FluxICONFONT_18 = Fonts.FluxICONFONT.FluxICONFONT.ofSize(18);
        }

        public static final class FluxICONFONT_40 {

            public static final FontRenderer FluxICONFONT_40 = Fonts.FluxICONFONT.FluxICONFONT.ofSize(40);
        }
    }

    public interface ICONFONT {

        FontFamily ICONFONT = Fonts.FONT_MANAGER.fontFamily(FontType.ICONFONT);

        public static final class ICONFONT_50 {

            public static final FontRenderer ICONFONT_50 = Fonts.ICONFONT.ICONFONT.ofSize(50);
        }

        public static final class ICONFONT_35 {

            public static final FontRenderer ICONFONT_35 = Fonts.ICONFONT.ICONFONT.ofSize(35);
        }

        public static final class ICONFONT_32 {

            public static final FontRenderer ICONFONT_32 = Fonts.ICONFONT.ICONFONT.ofSize(32);
        }

        public static final class ICONFONT_24 {

            public static final FontRenderer ICONFONT_24 = Fonts.ICONFONT.ICONFONT.ofSize(24);
        }

        public static final class ICONFONT_20 {

            public static final FontRenderer ICONFONT_20 = Fonts.ICONFONT.ICONFONT.ofSize(20);
        }

        public static final class ICONFONT_18 {

            public static final FontRenderer ICONFONT_18 = Fonts.ICONFONT.ICONFONT.ofSize(18);
        }

        public static final class ICONFONT_17 {

            public static final FontRenderer ICONFONT_17 = Fonts.ICONFONT.ICONFONT.ofSize(17);
        }

        public static final class ICONFONT_16 {

            public static final FontRenderer ICONFONT_16 = Fonts.ICONFONT.ICONFONT.ofSize(16);
        }
    }

    public interface OXIDE {

        FontFamily OXIDE = Fonts.FONT_MANAGER.fontFamily(FontType.OXIDE);

        public static final class OXIDE_18 {

            public static final FontRenderer OXIDE_18 = Fonts.OXIDE.OXIDE.ofSize(16);
        }

        public static final class OXIDE_55 {

            public static final FontRenderer OXIDE_55 = Fonts.OXIDE.OXIDE.ofSize(40);
        }
    }
}
