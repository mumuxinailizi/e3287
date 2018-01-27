package cn.e3mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.mapper.TbItemCatMapper;
import cn.e3mall.pojo.TbItemCat;
import cn.e3mall.pojo.TbItemCatExample;
import cn.e3mall.pojo.TbItemCatExample.Criteria;
import cn.e3mall.service.ItemCatService;
@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	@Override
	public List<EasyUITreeNode> getCatList(long parentId) {
		// TODO Auto-generated method stub
		//1.根据parentId进行查询
		//封装条件
		TbItemCatExample tbItemCatExample=new TbItemCatExample();
		Criteria criteria = tbItemCatExample.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> TbItemCats = tbItemCatMapper.selectByExample(tbItemCatExample);
		// 2、转换成EasyUITreeNode列表。
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (TbItemCat tbItemCat : TbItemCats) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		
		return resultList;
	}

}
