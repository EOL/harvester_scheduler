/**
 * Created by maha.mostafa on 6/2/2018.
 */

package org.bibalex.eol.scheduler.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class PropertiesFile {

    private String harvestVar;

    private String harvesterServiceUrl;

    private String proxyUserName;

    private String password;

    private String proxyExists;

    private String proxy;

    private String port;

    public String getProxyUserName() {
        return proxyUserName;
    }

    public void setProxyUserName(String userName) {
        this.proxyUserName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProxyExists() {
        return proxyExists;
    }

    public void setProxyExists(String proxyExists) {
        this.proxyExists = proxyExists;
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHarvestVar() {
        return harvestVar;
    }

    public void setHarvestVar(String harvestVar) {
        this.harvestVar = harvestVar;
    }

    public String getHarvesterServiceUrl() {
        return harvesterServiceUrl;
    }

    public void setHarvesterServiceUrl(String harvesterServiceUrl) {
        this.harvesterServiceUrl = harvesterServiceUrl;
    }

}
