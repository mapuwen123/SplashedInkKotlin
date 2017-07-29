package com.marvin.splashedinkkotlin.bean

/**
 * Created by Administrator on 2017/7/11.
 */

class PhotoBean {

    /**
     * id : OL5cqauxUS4
     * created_at : 2017-06-08T05:07:49-04:00
     * updated_at : 2017-07-11T03:30:08-04:00
     * width : 1600
     * height : 2000
     * color : #7AACCE
     * likes : 138
     * liked_by_user : false
     * description : null
     * user : {"id":"TDfYMpwQTho","updated_at":"2017-07-11T03:30:08-04:00","username":"thesollers","name":"Anton Darius | Sollers","first_name":"Anton","last_name":"Darius | Sollers","twitter_username":"DariusAnton","portfolio_url":"https://creativemarket.com/sollersphotography","bio":"I love bokeh! I LOVE UNSPLASH ","location":"Brasov , Romania","total_likes":354,"total_photos":198,"total_collections":4,"profile_image":{"small":"https://images.unsplash.com/profile-1494662322449-b7405a7b9a3c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=1e9e2111a1126b4a4a034250a6f07049","medium":"https://images.unsplash.com/profile-1494662322449-b7405a7b9a3c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=4664aba3a331aebce74d5b2f7824882e","large":"https://images.unsplash.com/profile-1494662322449-b7405a7b9a3c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=72134f04a2aeece19803918aa47fb4f3"},"links":{"self":"https://api.unsplash.com/users/thesollers","html":"http://unsplash.com/@thesollers","photos":"https://api.unsplash.com/users/thesollers/photos","likes":"https://api.unsplash.com/users/thesollers/likes","portfolio":"https://api.unsplash.com/users/thesollers/portfolio","following":"https://api.unsplash.com/users/thesollers/following","followers":"https://api.unsplash.com/users/thesollers/followers"}}
     * current_user_collections : []
     * urls : {"raw":"https://images.unsplash.com/photo-1496912819863-5055eb23c282","full":"https://images.unsplash.com/photo-1496912819863-5055eb23c282?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&s=094fdda8c9a5ba39c7ca3a564bc0147b","regular":"https://images.unsplash.com/photo-1496912819863-5055eb23c282?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&s=3e2c98c59fc9fda9fab682e0deed218d","small":"https://images.unsplash.com/photo-1496912819863-5055eb23c282?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&s=c22feb924610f480c159543f82a61149","thumb":"https://images.unsplash.com/photo-1496912819863-5055eb23c282?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&s=fd22fe4c5f7be38f29322d956b048168"}
     * categories : []
     * links : {"self":"https://api.unsplash.com/photos/OL5cqauxUS4","html":"http://unsplash.com/photos/OL5cqauxUS4","download":"http://unsplash.com/photos/OL5cqauxUS4/download","download_location":"https://api.unsplash.com/photos/OL5cqauxUS4/download"}
     */

    var id: String? = null
    var created_at: String? = null
    var updated_at: String? = null
    var width: Int = 0
    var height: Int = 0
    var color: String? = null
    var likes: Int = 0
    var isLiked_by_user: Boolean = false
    var description: Any? = null
    var user: UserBean? = null
    var urls: UrlsBean? = null
    var links: LinksBeanX? = null
    var current_user_collections: List<*>? = null
    var categories: List<*>? = null

    class UserBean {
        /**
         * id : TDfYMpwQTho
         * updated_at : 2017-07-11T03:30:08-04:00
         * username : thesollers
         * name : Anton Darius | Sollers
         * first_name : Anton
         * last_name : Darius | Sollers
         * twitter_username : DariusAnton
         * portfolio_url : https://creativemarket.com/sollersphotography
         * bio : I love bokeh! I LOVE UNSPLASH
         * location : Brasov , Romania
         * total_likes : 354
         * total_photos : 198
         * total_collections : 4
         * profile_image : {"small":"https://images.unsplash.com/profile-1494662322449-b7405a7b9a3c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=1e9e2111a1126b4a4a034250a6f07049","medium":"https://images.unsplash.com/profile-1494662322449-b7405a7b9a3c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=4664aba3a331aebce74d5b2f7824882e","large":"https://images.unsplash.com/profile-1494662322449-b7405a7b9a3c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=72134f04a2aeece19803918aa47fb4f3"}
         * links : {"self":"https://api.unsplash.com/users/thesollers","html":"http://unsplash.com/@thesollers","photos":"https://api.unsplash.com/users/thesollers/photos","likes":"https://api.unsplash.com/users/thesollers/likes","portfolio":"https://api.unsplash.com/users/thesollers/portfolio","following":"https://api.unsplash.com/users/thesollers/following","followers":"https://api.unsplash.com/users/thesollers/followers"}
         */

        var id: String? = null
        var updated_at: String? = null
        var username: String? = null
        var name: String? = null
        var first_name: String? = null
        var last_name: String? = null
        var twitter_username: String? = null
        var portfolio_url: String? = null
        var bio: String? = null
        var location: String? = null
        var total_likes: Int = 0
        var total_photos: Int = 0
        var total_collections: Int = 0
        var profile_image: ProfileImageBean? = null
        var links: LinksBean? = null

        class ProfileImageBean {
            /**
             * small : https://images.unsplash.com/profile-1494662322449-b7405a7b9a3c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=1e9e2111a1126b4a4a034250a6f07049
             * medium : https://images.unsplash.com/profile-1494662322449-b7405a7b9a3c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=4664aba3a331aebce74d5b2f7824882e
             * large : https://images.unsplash.com/profile-1494662322449-b7405a7b9a3c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=72134f04a2aeece19803918aa47fb4f3
             */

            var small: String? = null
            var medium: String? = null
            var large: String? = null
        }

        class LinksBean {
            /**
             * self : https://api.unsplash.com/users/thesollers
             * html : http://unsplash.com/@thesollers
             * photos : https://api.unsplash.com/users/thesollers/photos
             * likes : https://api.unsplash.com/users/thesollers/likes
             * portfolio : https://api.unsplash.com/users/thesollers/portfolio
             * following : https://api.unsplash.com/users/thesollers/following
             * followers : https://api.unsplash.com/users/thesollers/followers
             */

            var self: String? = null
            var html: String? = null
            var photos: String? = null
            var likes: String? = null
            var portfolio: String? = null
            var following: String? = null
            var followers: String? = null
        }
    }

    class UrlsBean {
        /**
         * raw : https://images.unsplash.com/photo-1496912819863-5055eb23c282
         * full : https://images.unsplash.com/photo-1496912819863-5055eb23c282?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&s=094fdda8c9a5ba39c7ca3a564bc0147b
         * regular : https://images.unsplash.com/photo-1496912819863-5055eb23c282?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&s=3e2c98c59fc9fda9fab682e0deed218d
         * small : https://images.unsplash.com/photo-1496912819863-5055eb23c282?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&s=c22feb924610f480c159543f82a61149
         * thumb : https://images.unsplash.com/photo-1496912819863-5055eb23c282?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&s=fd22fe4c5f7be38f29322d956b048168
         */

        var raw: String? = null
        var full: String? = null
        var regular: String? = null
        var small: String? = null
        var thumb: String? = null
    }

    class LinksBeanX {
        /**
         * self : https://api.unsplash.com/photos/OL5cqauxUS4
         * html : http://unsplash.com/photos/OL5cqauxUS4
         * download : http://unsplash.com/photos/OL5cqauxUS4/download
         * download_location : https://api.unsplash.com/photos/OL5cqauxUS4/download
         */

        var self: String? = null
        var html: String? = null
        var download: String? = null
        var download_location: String? = null
    }
}
