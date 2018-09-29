package open.movie.mvp.model.bean

data class HomeBean(
        var nextPageUrl: String?,
        var nextPublishTime: Long,
        var newestIssueType: String?,
        var dialog: Any?,
        var issueList: List<HomeBean.IssueListBean>?
) {
    data class IssueListBean(
            var releaseTime: Long,
            var type: String?,
            var date: Long,
            var publishTime: Long,
            var count: Int,
            var itemList: List<HomeBean.IssueListBean.ItemListBean>?
    ) {
        data class ItemListBean(
                var type: String?,
                var data: HomeBean.IssueListBean.ItemListBean.DataBean,
                var tag: Any?
        ) {
            data class DataBean(
                    var dataType: String?,
                    var id: Int,
                    var title: String?,
                    var description: String?,
                    var image: String?,
                    var actionUrl: String?,
                    var adTrack: Any?,
                    var isShade: Boolean,
                    var label: Any?,
                    var labelList: Any?,
                    var header: Any?,
                    var category: String?,
                    var duration: Long?,
                    var playUrl: String,
                    var cover: HomeBean.IssueListBean.ItemListBean.DataBean.CoverBean?,
                    var author: HomeBean.IssueListBean.ItemListBean.DataBean.AuthorBean?,
                    var releaseTime: Long?,
                    var consumption: HomeBean.IssueListBean.ItemListBean.DataBean.ConsumptionBean?
            ) {
                data class CoverBean(
                        var feed: String?,
                        var detail: String?,
                        var blurred: String?,
                        var sharing: String?,
                        var homepage: String?
                )

                data class ConsumptionBean(
                        var collectionCount: Int,
                        var shareCount: Int,
                        var replyCount: Int
                )

                data class AuthorBean(var icon: String)
            }
        }
    }
}


