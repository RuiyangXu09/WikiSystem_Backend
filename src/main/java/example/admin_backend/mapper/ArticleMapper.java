package example.admin_backend.mapper;

import example.admin_backend.domain.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    /**
     * 新增文章
     * @param article
     */
    @Insert("INSERT INTO article(title, summary, cover_img, state, category_id, create_user, create_time, update_time) " +
            "VALUES (#{title}, #{summary}, #{coverImg}, #{state}, #{categoryId}, #{createUser}, #{createTime}, #{updateTime})  ")
    void addArticle(Article article);

    /**
     * 获取数据库中的总记录数
     * @return
     */
    @Select("SELECT COUNT(*) FROM article")
    Integer countRows();

    /**
     * 分页查询，使用left join关联查询
     * @param startIndex 起始索引
     * @param pageSize 总页码数
     * @return
     */
    @Select("SELECT article.*, category.category_name FROM article LEFT JOIN category ON article.category_id = category.id LIMIT #{startIndex}, #{pageSize}")
    List<Article> pageArticleList(Integer startIndex, Integer pageSize);

    /**
     * 获取文章信息
     * @param id 传入的id
     * @return
     */
    @Select("SELECT * FROM article WHERE id = #{id}")
    Article getArticleDetails(Integer id);

    /**
     * 修改文章信息
     * @param article
     */
    @Update("UPDATE article SET title = #{title}, summary = #{summary}, cover_img = #{coverImg}, state = #{state}, update_time = #{updateTime} WHERE id = #{id}")
    void updateArticle(Article article);

    /**
     * 删除文章
     * @param id
     */
    @Delete("DELETE FROM article WHERE id = #{id}")
    void deleteArticle(Integer id);
}
