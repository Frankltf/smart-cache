package smart.cache.config;

/**
 * @Intro
 * @Author liutengfei
 */
public class SmartCacheConfig {
    private String dbName ;
    private String retentionPolicy;
    private String url;
    private String username;
    private String password;
    private boolean disabled;

    public void setRetentionPolicy(String retentionPolicy) {
        this.retentionPolicy = retentionPolicy;
    }

    public String getDbName() {
        return dbName;
    }

    public String getRetentionPolicy() {
        return retentionPolicy;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isDisabled() {
        return disabled;
    }

    @Override
    public String toString() {
        return "HystrixMonitorConfig{" +
                "dbName='" + dbName + '\'' +
                ", retentionPolicy='" + retentionPolicy + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public SmartCacheConfig(Builder builder) {
        this.dbName = builder.dbName;
        this.retentionPolicy = builder.retentionPolicy;
        this.url = builder.url;
        this.username = builder.username;
        this.password = builder.password;
        this.disabled = builder.disabled;
    }

    public static class Builder{
        String dbName ;
        String retentionPolicy;
        String url;
        String username;
        String password;
        boolean disabled;

        public Builder(){}
        Builder(SmartCacheConfig smartCacheConfig){
            this.dbName = smartCacheConfig.dbName;
            this.retentionPolicy = smartCacheConfig.retentionPolicy;
            this.url = smartCacheConfig.url;
            this.username = smartCacheConfig.username;
            this.password = smartCacheConfig.password;
        }

        public Builder setDbName(String dbName) {
            this.dbName = dbName;
            return this;
        }

        public Builder setRetentionPolicy(String retentionPolicy) {
            this.retentionPolicy = retentionPolicy;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setDisabled(boolean disabled) {
            this.disabled = disabled;
            return this;
        }

        public SmartCacheConfig build(){
            return new SmartCacheConfig(this);
        }
    }
}
