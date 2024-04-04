package example.admin_backend.controller;

import example.admin_backend.domain.Category;
import example.admin_backend.service.CategoryService;
import example.admin_backend.utils.Result;
import example.admin_backend.utils.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {
    /**
     * 注入categoryService的bean
     */
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类，传入category的对象，使用注解@RequestBody，以json格式读取请求数据，然后转换为一个category类的对象
     * @param category
     * @return
     */
    @PostMapping(value = "/addCategory")
    public Result<Category> addCategory(@RequestBody Category category){
        if (category.getCategoryName() != null && !category.getCategoryName().isEmpty()){
            categoryService.addCategory(category);
            return Result.success();
        }else {
            return Result.error("category name cannot be empty.");
        }
    }

    /**
     * 分类列表查询
     * @return 返回一个list集合类型的数据
     */
    @GetMapping(value = "/getCategoryList")
    public Result<List<Category>> getCategoryList(){
        List<Category> categoryList = categoryService.getCategoryList();
        return Result.success(categoryList);
    }

    /**
     * 根据id获取分类详情
     * @param id 前端页面传入的id值
     * @return
     */
    @GetMapping(value = "/getCategoryDetails")
    public Result<Category> getCategoryDetails(Integer id){
        //判断当前查询的分类id创建者是否等于当前登录的用户id
        Map<String, Object> map = ThreadLocalUtils.get();
        //获取当前线程的id值
        Integer userId = (Integer) map.get("id");
        //创建一个Category对象来接收查询返回的数据，其中包含该分类的创建者的id，createUser，getCategoryDetails传入的id是前端输入的id
        Category category = categoryService.getCategoryDetails(id);
        //判断，分类创建者的id是否等于当前线程的id
        if (Objects.equals(category.getCreateUser(), userId)){
            //等于，输出success信息，包含category对象的数据
            return Result.success(category);
        }else {
            //不等于，返回一个错误提示信息
            return Result.error("Not your Information.");
        }

    }
}
