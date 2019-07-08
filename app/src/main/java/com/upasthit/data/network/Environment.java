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
            return "";
        }

        @Override
        public String getAwsBucketName() {
            return "";
        }

    }, PRODUCTION {
        @Override
        public String getBaseUrl() {
            return "http://af293494236ee11e9989602fe773b974-224893321.us-east-1.elb.amazonaws.com/";
        }

        @Override
        public String getAwsBucketName() {
            return "";
        }

    };

    public abstract String getBaseUrl();

    public abstract String getAwsBucketName();

}
