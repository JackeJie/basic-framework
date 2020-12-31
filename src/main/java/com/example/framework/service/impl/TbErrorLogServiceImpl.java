package com.example.framework.service.impl;

import com.example.framework.entity.TbErrorLog;
import com.example.framework.mapper.TbErrorLogMapper;
import com.example.framework.service.ITbErrorLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 错误日志信息 服务实现类
 * </p>
 *
 * @author wangjie
 * @since 2020-12-29
 */
@Service
public class TbErrorLogServiceImpl extends ServiceImpl<TbErrorLogMapper, TbErrorLog> implements ITbErrorLogService {

}
