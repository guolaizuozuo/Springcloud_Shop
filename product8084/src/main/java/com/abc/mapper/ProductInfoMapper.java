package com.abc.mapper;

import com.abc.dataobject.ProductInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductInfoMapper {

    @Select("select * from product_info where product_status = #{productStatus}")
    List<ProductInfo> findByProductStatus(Integer productStatus);

    @Select("<script>"
            + "SELECT * FROM product_info WHERE product_id IN "
            + "<foreach item='item' index='index' collection='productIdList' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</script>")
    List<ProductInfo> findByProductIdIn(@Param("productIdList") List<String> productIdList);

    @Update({ "update product_info set product_name = #{productName},product_stock = #{productStock},update_time = #{updateTime, jdbcType=TIMESTAMP} where product_id = #{productId}" })
    int save(ProductInfo productInfo);

    @Select("select * from product_info where product_id = #{id}")
    Optional<ProductInfo> findById(String id);
}
