package com.ogym.project.user.kakao;

import lombok.Data;

@Data

public class KakaoProfile {

    public Long id;
    public String connected_at;
    public Properties properties;
    public KakaoAccount kakao_account;
    @Data
    public class Properties {

        public String nickname;
        public String profile_image;
        public String thumbnail_image;

    }
    @Data
    public class KakaoAccount {

        public Boolean profile_nickname_needs_agreement;
        public Boolean profile_image_needs_agreement;
        public Profile profile;
        public Boolean has_gender;
        public Boolean gender_needs_agreement;
        public String gender;
        public Boolean has_Email;
        public String email;

        @Data
        public class Profile {

            public String nickname;
            public String thumbnail_image_url;
            public String profile_imageUrl;
            public Boolean is_default_image;

        }
    }
}




