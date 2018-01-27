package cn.e3mall.service.impl;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;

/**
 * 商品管理服务
 * <p>Title: ItemServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Override
	public TbItem getItemById(long itemId) {
		TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
		return tbItem;
	}

	@Override
	public DataGridResult getItemList(Integer page, Integer rows) {
		// TODO Auto-generated method stub
		//设置分页信息
				PageHelper.startPage(page, rows);
				//执行查询
				TbItemExample example = new TbItemExample();
				List<TbItem> list = itemMapper.selectByExample(example);
				//取分页信息
				PageInfo<TbItem> pageInfo = new PageInfo<>(list);
				
				//创建返回结果对象
				DataGridResult result = new DataGridResult();
				result.setTotal(pageInfo.getTotal());
				
				result.setRows(list);
				return result;

	}

}
