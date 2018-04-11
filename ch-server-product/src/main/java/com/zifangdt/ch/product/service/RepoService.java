package com.zifangdt.ch.product.service;

import com.zifangdt.ch.base.dto.product.entity.Repo;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.exception.DataNotFoundException;
import com.zifangdt.ch.base.exception.WrongOperationException;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.base.service.CustomQuery;
import com.zifangdt.ch.product.mapper.RepoMapper;
import com.zifangdt.ch.product.mapper.RepoProductMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RepoService extends BaseService<Repo, Long>{

    @Autowired
    RepoMapper repoMapper;

    @Autowired
    RepoProductMapper repoProductMapper;

    @Override
    protected void preSave(Repo toBeSaved) {
        CustomQuery customQuery = CustomQuery.builder().andCondition("is_default is true").build();
        // 保证至少有一个默认仓库
        if (count(customQuery) == 0){
            toBeSaved.setIsDefault(true);
        }
    }

    public void delete(long id){
        Repo repo = get(id);
        if (repo == null) throw new DataNotFoundException("仓库不存在");
        if (repoMapper.isRepoUsed(id)){
            throw new WrongOperationException("该仓库还有库存，不能删除");
        }
        repoMapper.deleteByPrimaryKey(id);
        repoProductMapper.deleteByRepo(id);
    }

    public void toggleValid(long id){
        repoMapper.toggleValid(id);
    }

    /**
     * 需要显示创建者名字，在完成sql查询后，调用UAA微服务获取用户姓名
     * @return
     */
    @Override
    public List<Repo> findAll() {
        List<Repo> list = super.findAll();
        return list;
    }

    public Repo getDefaultRepo(){
        Repo repo = repoMapper.findDefault();
        if (repo ==  null) {
            throw new DataInvalidException("找不到默认仓库");
        }
        return repo;
    }

    /**
     * 获取产品在所有仓库的库存，如果没有库存，则为0
     * @param productId
     * @return
     */
    public List<Repo> getProductRepo(Long productId){
        return repoMapper.findRepoNumberForProduct(productId);
    }

}
