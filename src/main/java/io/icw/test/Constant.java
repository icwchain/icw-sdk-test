
package io.icw.test;

import java.util.Locale;

public class Constant {

    /**
     * 四国语言
     */
    public enum Language {

        zh("zh", 1, Locale.SIMPLIFIED_CHINESE),
        en("en", 2, Locale.ENGLISH),
        ko("ko", 3, Locale.KOREA),
        jp("jp", 4, Locale.JAPAN),
        ru("ru", 5, new Locale("RU")),//俄罗斯语
        my("my", 6, new Locale("MY")),//马来语
        th("th", 7, new Locale("TH"));//泰文

        private String lan;

        private Integer lanCode;

        private Locale locale;

        Language(String lan, Integer lanCode, Locale locale) {
            this.lan = lan;
            this.lanCode = lanCode;
            this.locale = locale;
        }

        public static Language getByLan(String lan){
            for(Language language : Language.values()){
                if(language.lan.equals(lan)){
                    return language;
                }
            }
            //默认值为中文 避免出现异常.
            return Language.zh;
        }

        public final String getLan() {
            return this.lan;
        }

        public final Integer getLanCode() {
            return this.lanCode;
        }

        public final Locale getLocale() {
            return this.locale;
        }
    }

}
