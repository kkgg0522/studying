package hello.itemservice.repository.mybatis;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemSearchCondition;
import hello.itemservice.repository.ItemUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ItemMapper {
    void save(Item item);
            //1개인 경우는 생략가능 2개 이상 @Param 지정
    void update(@Param("id") long id, @Param("updateParam") ItemUpdateDto updateItem);

    Optional<Item> findById(long id);

    List<Item> findAll(ItemSearchCondition item);
}
