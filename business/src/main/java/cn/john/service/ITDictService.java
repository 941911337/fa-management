package cn.john.service;

import cn.john.model.TDict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author John Yan
 * @since 2021-07-24
 */
public interface ITDictService extends IService<TDict> {
    /**
     * 获取全部字典
     * @return
     */
     Map<String, List<TDict>> getAllDict();

}
