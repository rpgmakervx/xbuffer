package org.easyarch.xbuffer.kernel.common.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xingtianyu on 2018/11/30.
 */
public class ModuleBuilder implements Iterable<AbstractModule>  {

    List<AbstractModule> modules = new ArrayList<>();

    private Injector injector;

    public void addModule(AbstractModule module){
        modules.add(module);
    }

    public void createInjector(){
        this.injector = Guice.createInjector(modules);
    }

    public Injector injector(){
        return this.injector;
    }

    @Override
    public Iterator<AbstractModule> iterator() {
        return modules.iterator();
    }
}
