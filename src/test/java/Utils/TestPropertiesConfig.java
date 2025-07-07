package Utils;

import org.aeonbits.owner.Config;

import static Utils.Constants.BASE_URL_UI;

@Config.Sources({
        "classpath:${env}.properties",
        "classpath:default.properties"
})
public interface TestPropertiesConfig extends Config {

    @Config.Key("baseUrl")
    @Config.DefaultValue(BASE_URL_UI)
    String getBaseURl();

    @Config.Key("login")
    String getLogin();

    @Config.Key("password")
    String getPassword();

    @Config.Key("remoteUrl")
    String remoteUrl();

    @Config.Key("loginForSignIn")
    String getLoginForSignIn();

    @Config.Key("passwordForSignIn")
    String getPasswordForSignIn();
}
