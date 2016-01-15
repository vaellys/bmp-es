package com.ist.ioc.service.common.elasticsearch.impl.commonPool;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

public class TestPoolableFactory implements PoolableObjectFactory<BaseObject> {

    // 重新初始化实例返回池
    @Override
    public void activateObject(BaseObject arg0) throws Exception {
        arg0.setActive(true);
    }

    // 销毁被破坏的实例
    @Override
    public void destroyObject(BaseObject arg0) throws Exception {
        arg0 = null;
    }

    // 创建一个实例到对象池
    @Override
    public BaseObject makeObject() throws Exception {
        BaseObject bo = new BaseObject();
        return bo;
    }

    // 取消初始化实例返回到空闲对象池
    @Override
    public void passivateObject(BaseObject arg0) throws Exception {
        arg0.setActive(false);

    }

    // 验证该实例是否安全
    @Override
    public boolean validateObject(BaseObject arg0) {
        if (arg0.isActive())
            return true;
        else
            return false;
    }
    
    public static void main(String[] args) {
        BaseObject bo = null;  
        PoolableObjectFactory<BaseObject> factory = new TestPoolableFactory();  
        GenericObjectPool<BaseObject> pool = new GenericObjectPool<BaseObject>(factory);  
        //这里两种池都可以，区别下文会提到  
        //ObjectPool pool = new StackObjectPool(factory);  
        try {  
            for(int i = 0; i < 5; i++) {  
                System.out.println("\n==========="+i+"===========");  
                System.out.println("池中处于闲置状态的实例pool.getNumIdle()："+pool.getNumIdle());  
                //从池里取一个对象，新创建makeObject或将以前闲置的对象取出来  
                bo = (BaseObject)pool.borrowObject();  
                System.out.println("bo:"+bo);  
                System.out.println("池中所有在用实例数量pool.getNumActive()："+pool.getNumActive());  
                if((i%2) == 0) {  
                    //用完之后归还对象  
                    pool.returnObject(bo);  
                    System.out.println("归还对象！！！！");  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if(bo != null) {  
                    pool.returnObject(bo);  
                }  
                //关闭池  
                pool.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }
    }
    
}
