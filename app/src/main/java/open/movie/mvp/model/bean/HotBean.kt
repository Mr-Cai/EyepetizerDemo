package open.movie.mvp.model.bean

data class HotBean(
        var count: Int,
        var total: Int,
        var nextPageUrl: Any?,
        var itemList: List<HotBean.ItemListBean>?
) {
    data class ItemListBean(
            var type: String?,
            var data: HotBean.ItemListBean.DataBean?,
            var tag: Any?
    ) {
        data class DataBean(
                var dataType: String?,
                var id: Int,
                var title: String?,
                var slogan: Any?,
                var description: String?,
                var provider: HotBean.ItemListBean.DataBean.ProviderBean?,
                var category: String?,
                var author: Any?,
                var cover: HotBean.ItemListBean.DataBean.CoverBean?,
                var playUrl: String?,
                var thumbPlayUrl: Any?,
                var duration: Long,
                var releaseTime: Long,
                var library: String?,
                var consumption: HotBean.ItemListBean.DataBean.ConsumptionBean?,
                var campaign: Any?,
                var waterMarks: Any?,
                var adTrack: Any?,
                var type: String?,
                var titlePgc: Any?,
                var descriptionPgc: Any?,
                var remark: Any?,
                var idx: Int,
                var shareAdTrack: Any?,
                var favoriteAdTrack: Any?,
                var webAdTrack: Any?,
                var date: Long,
                var promotion: Any?,
                var label: Any?,
                var descriptionEditor: String?,
                var isCollected: Boolean,
                var isPlayed: Boolean,
                var lastViewTime: Any?,
                var playInfo: List<HotBean.ItemListBean.DataBean.PlayInfoBean>?,
                var tags: List<HotBean.ItemListBean.DataBean.TagsBean>?,
                var labelList: List<*>?,
                var subtitles: List<*>?
        ) {
            data class ProviderBean(
                    var name: String?,
                    var alias: String?,
                    var icon: String?
            )

            data class CoverBean(
                    var feed: String?,
                    var detail: String?,
                    var blurred: String?,
                    var sharing: Any?,
                    var homepage: Any?
            )

            data class ConsumptionBean(
                    var collectionCount: Int,
                    var shareCount: Int,
                    var replyCount: Int
            )

            data class PlayInfoBean(
                    var height: Int,
                    var width: Int,
                    var name: String?,
                    var type: String?,
                    var url: String?,
                    var urlList: List<HotBean.ItemListBean.DataBean.PlayInfoBean.UrlListBean>?
            ) {
                data class UrlListBean(
                        var name: String?,
                        var url: String?,
                        var size: Int
                )
            }

            data class TagsBean(
                    var id: Int,
                    var name: String?,
                    var actionUrl: String?,
                    var adTrack: Any?
            )
        }
    }
}
