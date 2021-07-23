package cn.john.service;

import cn.john.dao.DictMapper;
import cn.john.model.Dict;
import cn.john.model.DictExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author John Yan
 */
@Slf4j
@Service
public class DictService extends BaseService<DictMapper> {


    public  Map<String, List<Dict>> getAllDict(){
        DictExample dictExample = new DictExample();
        DictExample.Criteria criteria = dictExample.createCriteria();
        criteria.andIsDelEqualTo(0);
        dictExample.setOrderByClause("sort");
        List<Dict> list = baseMapper.selectByExample(dictExample);
        return list.stream().collect(Collectors.groupingBy(Dict::getType));
    }


}
