package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.OfficeMapper;
import com.ruoyi.system.domain.Office;
import com.ruoyi.system.service.IOfficeService;

/**
 * 合同文件Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-12-02
 */
@Service
public class OfficeServiceImpl implements IOfficeService 
{
    @Autowired
    private OfficeMapper officeMapper;

    /**
     * 查询合同文件
     * 
     * @param id 合同文件主键
     * @return 合同文件
     */
    @Override
    public Office selectOfficeById(Long id)
    {
        return officeMapper.selectOfficeById(id);
    }

    /**
     * 查询合同文件列表
     * 
     * @param office 合同文件
     * @return 合同文件
     */
    @Override
    public List<Office> selectOfficeList(Office office)
    {
        return officeMapper.selectOfficeList(office);
    }

    /**
     * 新增合同文件
     * 
     * @param office 合同文件
     * @return 结果
     */
    @Override
    public int insertOffice(Office office)
    {
        office.setCreateTime(DateUtils.getNowDate());
        return officeMapper.insertOffice(office);
    }

    /**
     * 修改合同文件
     * 
     * @param office 合同文件
     * @return 结果
     */
    @Override
    public int updateOffice(Office office)
    {
        return officeMapper.updateOffice(office);
    }

    /**
     * 批量删除合同文件
     * 
     * @param ids 需要删除的合同文件主键
     * @return 结果
     */
    @Override
    public int deleteOfficeByIds(Long[] ids)
    {
        return officeMapper.deleteOfficeByIds(ids);
    }

    /**
     * 删除合同文件信息
     * 
     * @param id 合同文件主键
     * @return 结果
     */
    @Override
    public int deleteOfficeById(Long id)
    {
        return officeMapper.deleteOfficeById(id);
    }
}
