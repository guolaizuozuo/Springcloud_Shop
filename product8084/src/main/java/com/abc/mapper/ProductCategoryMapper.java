package com.abc.mapper;

import com.abc.dataobject.ProductCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductCategoryMapper {

    @Select("<script>"
            + "SELECT * FROM product_category WHERE category_id IN "
            + "<foreach item='item' index='index' collection='categoryTypeList' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</script>")
    List<ProductCategory> findByCategoryTypeIn(@Param("categoryTypeList")List<Integer> categoryTypeList);
}
