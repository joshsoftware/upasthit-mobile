package com.upasthit.data.network;

public enum Environment {

    LOCAL {
        @Override
        public String getBaseUrl() {
            return "";
        }

        @Override
        public String getAwsBucketName() {
            return "";
        }

    }, STAGING {
        @Override
        public String getBaseUrl() {
            return "https://stage.upasthit.joshsoftware.com/";
        }

        @Override
        public String getAwsBucketName() {
            return "";
        }

    }, PRODUCTION {
        @Override
        public String getBaseUrl() {
            return "";
        }

        @Override
        public String getAwsBucketName() {
            return "";
        }

    };

    public abstract String getBaseUrl();

    public abstract String getAwsBucketName();

}
