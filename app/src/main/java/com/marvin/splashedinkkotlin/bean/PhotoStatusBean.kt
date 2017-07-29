package com.marvin.splashedinkkotlin.bean

/**
 * Created by Administrator on 2017/7/13.
 */

class PhotoStatusBean {

    /**
     * id : JRSYzMhMJvE
     * created_at : 2017-07-11T19:09:34-04:00
     * updated_at : 2017-07-12T20:53:27-04:00
     * width : 3903
     * height : 5846
     * color : #6BA5D8
     * slug : null
     * downloads : 293
     * likes : 73
     * views : 38161
     * liked_by_user : false
     * description : null
     * exif : {"make":"Nikon","model":"NIKON D750","exposure_time":"15","aperture":"4","focal_length":"38","iso":3200}
     * location : {"title":"Hurricane Ridge, United States","name":"Hurricane Ridge","city":null,"country":"United States","position":{"latitude":47.9331457,"longitude":-123.4096267}}
     * current_user_collections : []
     * urls : {"raw":"https://images.unsplash.com/photo-1499814526117-353c14753c9b","full":"https://images.unsplash.com/photo-1499814526117-353c14753c9b?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&s=88f91643ca7631684886ea4fa152f692","regular":"https://images.unsplash.com/photo-1499814526117-353c14753c9b?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&s=b46c1c7cdd680214a794522b4adf0254","small":"https://images.unsplash.com/photo-1499814526117-353c14753c9b?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&s=6316c7735eb33229b51cdd171ed7fade","thumb":"https://images.unsplash.com/photo-1499814526117-353c14753c9b?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&s=34cdd10f72ccade9cb06a0864fb333d4"}
     * categories : []
     * links : {"self":"https://api.unsplash.com/photos/JRSYzMhMJvE","html":"http://unsplash.com/photos/JRSYzMhMJvE","download":"http://unsplash.com/photos/JRSYzMhMJvE/download","download_location":"https://api.unsplash.com/photos/JRSYzMhMJvE/download"}
     * user : {"id":"-kS45lLTqmw","updated_at":"2017-07-12T21:20:55-04:00","username":"bobbystevenson","name":"Bobby Stevenson","first_name":"Bobby","last_name":"Stevenson","twitter_username":null,"portfolio_url":null,"bio":"","location":null,"total_likes":1,"total_photos":4,"total_collections":0,"profile_image":{"small":"https://images.unsplash.com/profile-fb-1499814342-0dfc365d0c13.jpg?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=64adebe00e0cca32029abc881289b055","medium":"https://images.unsplash.com/profile-fb-1499814342-0dfc365d0c13.jpg?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=4b926e29971fb5d5500545373ddf4d28","large":"https://images.unsplash.com/profile-fb-1499814342-0dfc365d0c13.jpg?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=a9f558a0c1b43c7a91eeeb2a59f981f9"},"links":{"self":"https://api.unsplash.com/users/bobbystevenson","html":"http://unsplash.com/@bobbystevenson","photos":"https://api.unsplash.com/users/bobbystevenson/photos","likes":"https://api.unsplash.com/users/bobbystevenson/likes","portfolio":"https://api.unsplash.com/users/bobbystevenson/portfolio","following":"https://api.unsplash.com/users/bobbystevenson/following","followers":"https://api.unsplash.com/users/bobbystevenson/followers"}}
     */

    var id: String? = null
    var created_at: String? = null
    var updated_at: String? = null
    var width: Int = 0
    var height: Int = 0
    var color: String? = null
    var slug: Any? = null
    var downloads: Int = 0
    var likes: Int = 0
    var views: Int = 0
    var isLiked_by_user: Boolean = false
    var description: Any? = null
    var exif: ExifBean? = null
    var location: LocationBean? = null
    var urls: UrlsBean? = null
    var links: LinksBean? = null
    var user: UserBean? = null
    var current_user_collections: List<*>? = null
    var categories: List<*>? = null

    class ExifBean {
        /**
         * make : Nikon
         * model : NIKON D750
         * exposure_time : 15
         * aperture : 4
         * focal_length : 38
         * iso : 3200
         */

        var make: String? = null
        var model: String? = null
        var exposure_time: String? = null
        var aperture: String? = null
        var focal_length: String? = null
        var iso: Int = 0
    }

    class LocationBean {
        /**
         * title : Hurricane Ridge, United States
         * name : Hurricane Ridge
         * city : null
         * country : United States
         * position : {"latitude":47.9331457,"longitude":-123.4096267}
         */

        var title: String? = null
        var name: String? = null
        var city: Any? = null
        var country: String? = null
        var position: PositionBean? = null

        class PositionBean {
            /**
             * latitude : 47.9331457
             * longitude : -123.4096267
             */

            var latitude: Double = 0.toDouble()
            var longitude: Double = 0.toDouble()
        }
    }

    class UrlsBean {
        /**
         * raw : https://images.unsplash.com/photo-1499814526117-353c14753c9b
         * full : https://images.unsplash.com/photo-1499814526117-353c14753c9b?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&s=88f91643ca7631684886ea4fa152f692
         * regular : https://images.unsplash.com/photo-1499814526117-353c14753c9b?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&s=b46c1c7cdd680214a794522b4adf0254
         * small : https://images.unsplash.com/photo-1499814526117-353c14753c9b?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&s=6316c7735eb33229b51cdd171ed7fade
         * thumb : https://images.unsplash.com/photo-1499814526117-353c14753c9b?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&s=34cdd10f72ccade9cb06a0864fb333d4
         */

        var raw: String? = null
        var full: String? = null
        var regular: String? = null
        var small: String? = null
        var thumb: String? = null
    }

    class LinksBean {
        /**
         * self : https://api.unsplash.com/photos/JRSYzMhMJvE
         * html : http://unsplash.com/photos/JRSYzMhMJvE
         * download : http://unsplash.com/photos/JRSYzMhMJvE/download
         * download_location : https://api.unsplash.com/photos/JRSYzMhMJvE/download
         */

        var self: String? = null
        var html: String? = null
        var download: String? = null
        var download_location: String? = null
    }

    class UserBean {
        /**
         * id : -kS45lLTqmw
         * updated_at : 2017-07-12T21:20:55-04:00
         * username : bobbystevenson
         * name : Bobby Stevenson
         * first_name : Bobby
         * last_name : Stevenson
         * twitter_username : null
         * portfolio_url : null
         * bio :
         * location : null
         * total_likes : 1
         * total_photos : 4
         * total_collections : 0
         * profile_image : {"small":"https://images.unsplash.com/profile-fb-1499814342-0dfc365d0c13.jpg?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=64adebe00e0cca32029abc881289b055","medium":"https://images.unsplash.com/profile-fb-1499814342-0dfc365d0c13.jpg?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=4b926e29971fb5d5500545373ddf4d28","large":"https://images.unsplash.com/profile-fb-1499814342-0dfc365d0c13.jpg?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=a9f558a0c1b43c7a91eeeb2a59f981f9"}
         * links : {"self":"https://api.unsplash.com/users/bobbystevenson","html":"http://unsplash.com/@bobbystevenson","photos":"https://api.unsplash.com/users/bobbystevenson/photos","likes":"https://api.unsplash.com/users/bobbystevenson/likes","portfolio":"https://api.unsplash.com/users/bobbystevenson/portfolio","following":"https://api.unsplash.com/users/bobbystevenson/following","followers":"https://api.unsplash.com/users/bobbystevenson/followers"}
         */

        var id: String? = null
        var updated_at: String? = null
        var username: String? = null
        var name: String? = null
        var first_name: String? = null
        var last_name: String? = null
        var twitter_username: Any? = null
        var portfolio_url: Any? = null
        var bio: String? = null
        var location: Any? = null
        var total_likes: Int = 0
        var total_photos: Int = 0
        var total_collections: Int = 0
        var profile_image: ProfileImageBean? = null
        var links: LinksBeanX? = null

        class ProfileImageBean {
            /**
             * small : https://images.unsplash.com/profile-fb-1499814342-0dfc365d0c13.jpg?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=64adebe00e0cca32029abc881289b055
             * medium : https://images.unsplash.com/profile-fb-1499814342-0dfc365d0c13.jpg?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=4b926e29971fb5d5500545373ddf4d28
             * large : https://images.unsplash.com/profile-fb-1499814342-0dfc365d0c13.jpg?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=a9f558a0c1b43c7a91eeeb2a59f981f9
             */

            var small: String? = null
            var medium: String? = null
            var large: String? = null
        }

        class LinksBeanX {
            /**
             * self : https://api.unsplash.com/users/bobbystevenson
             * html : http://unsplash.com/@bobbystevenson
             * photos : https://api.unsplash.com/users/bobbystevenson/photos
             * likes : https://api.unsplash.com/users/bobbystevenson/likes
             * portfolio : https://api.unsplash.com/users/bobbystevenson/portfolio
             * following : https://api.unsplash.com/users/bobbystevenson/following
             * followers : https://api.unsplash.com/users/bobbystevenson/followers
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
}
