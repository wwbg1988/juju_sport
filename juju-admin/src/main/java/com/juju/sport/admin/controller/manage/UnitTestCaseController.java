package com.juju.sport.admin.controller.manage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Created by Peter on 14-9-14.
 */
@Controller
@RequestMapping("/unit/test")
public class UnitTestCaseController {

	/*
    @RequestMapping(value = "/webTest.do")
    public String debug(){
        return "/debug";
    }


    @RequestMapping(value = "/execute.do")
    @ResponseBody
    public String execute(@RequestParam String beanName,@RequestParam String method,String ... json) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        return null;
    }

    private Object buildMethod(String beanName,String method,String [] classNames,String[] json) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        Method m = null;
        Object instance = SpringContextUtil.getBean(beanName);
        if(classNames!=null) {
            Class<?>[] paramterTypes = new Class<?>[classNames.length];
            int i = 0;
            for (String className : classNames) {
                paramterTypes[i] = Class.forName(className);
                i++;
            }
            m = instance.getClass().getDeclaredMethod(method, paramterTypes);
            // 处理json数据 暂时没有处理
            return m.invoke(instance,null);
        }else{
            m = instance.getClass().getDeclaredMethod(method);
            return m.invoke(instance);
        }
    }
    */
}
