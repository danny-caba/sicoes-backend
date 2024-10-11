package pe.gob.osinergmin.sicoes.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.AppenderControlArraySet;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.impl.DefaultLogEventFactory;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.hibernate.Hibernate;
import org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

/**
 * Aspecto que elmina la naturaleza de hibernate de un objeto. Se ejectua
 * después del retorno de las fachadas e inspecciona los objetos devueltos
 * convirtiendo los proxys de Hibernate en null.
 *
 * @author Sisifo
 *
 */
@Component
public class EliminaHibernateProxysAspect {
    private final Logger logger = LogManager.getLogger(getClass());

    public Object eliminarHibernateProxysAround(final ProceedingJoinPoint pjp) throws Throwable {
    	 final Object retVal = pjp.proceed();
         if(retVal==null) return null;
         if(retVal.getClass().getName().contains("PageImpl")) {
         	PageImpl pageImpl=(PageImpl)retVal;
         	List content=(List)inicializarSiEsNecesario2(pageImpl.getContent());
         	PageImpl pageImpl2=new PageImpl(content,pageImpl.getPageable(),pageImpl.getTotalElements());
         	return pageImpl2;
     	}else {
     		inicializarSiEsNecesario(retVal,"1 ");
         }
         return retVal;
    }

    private Object inicializarSiEsNecesario(Object object,String nivel) {
    	if (object == null) {
            return null;
        }      
        if(nivel.contains("producto")) {
        	int i=1;
        }
        if (!Hibernate.isInitialized(object)) {
//        	logger.info(nivel+" = null");
            return null;
        } else {
            if (object instanceof Collection) {
//            	logger.info(nivel+" = colecccion");
                return inicializarColeccion((Collection) object,nivel);
            } else {
//            	logger.info(nivel+" = objeto");            	
                return inicializarPropiedadesObjeto(object,nivel);
            }
        }

    }


    private boolean claseInvalida(Object object) {
    	if(object instanceof PropertiesUtil||
    			object instanceof StatusLogger||
    			object instanceof LoggerContext||
    			object instanceof DefaultLogEventFactory|| 
    			object instanceof Level||
    			object instanceof AppenderRef||
    			object instanceof StatusLogger||
    			object instanceof AppenderControlArraySet||
    			object instanceof DefaultLogEventFactory) {
    		return true;
    	}
		return false;
	}

	@SuppressWarnings("rawtypes")
    private Collection inicializarColeccion(Collection coleccion,String nivel) {
        for (Object object : coleccion) {
            inicializarSiEsNecesario(object,nivel);
        }

        return coleccion;
    }

    private Object inicializarPropiedadesObjeto(Object object,String nivel) {    	
        for (Field field : object.getClass().getDeclaredFields()) {
            if (fieldEsSusceptibleDeInicializarse(field)) {
                field.setAccessible(true);
                try {
                	if(!varibleInvalida(field)) {
                		//logger.info(nivel+">"+field.getName());
                		field.set(object, inicializarSiEsNecesario(field.get(object),nivel+">"+field.getName()));	
                	}
                    
                } catch (final Exception e) {
                    logger.error("Se ha producido un error al acceder al campo {} del objeto {}", new Object[] { field,
                            object }, e);
                    //throw new RuntimeException("Error al acceder a campo", e);
                }
            }
        }

        return object;
    }

    private boolean varibleInvalida(Field field) {
		if(field.getName().endsWith("logger")) {
			return true;
		}
		return false;
	}

	private boolean fieldEsSusceptibleDeInicializarse(final Field field) {
        if (Collection.class.isAssignableFrom(field.getType())) {
            return true;
        }

        if (!claseEsDeTipoBasico(field.getType()) && !clasePertenceALaEspecificiacionJava(field.getType())) {
            return true;
        }

        return false;
    }

    private boolean clasePertenceALaEspecificiacionJava(final Class<?> clase) {
        final String clasePropiedad = clase.getCanonicalName();
        if (clasePropiedad.startsWith("java.") || clasePropiedad.startsWith("javax.")) {
            return true;
        }
        return false;
    }

    private boolean claseEsDeTipoBasico(final Class<?> clase) {
        if (clase.isEnum() || clase.isPrimitive()) {
            return true;
        }
        return false;
    }
    

    @SuppressWarnings("rawtypes")
    private Object inicializarSiEsNecesario2(Object object) throws InstantiationException, IllegalAccessException {
        if (object == null) {
            //logger.info("object is null");

            return null;
        }
        if (!Hibernate.isInitialized(object)) {
        	 //logger.info(object.toString()+" is null");
            return null;
        } else {
            if (object instanceof Collection) {
            	//logger.info(object.toString()+" is Collection");
                return inicializarColeccion2((Collection) object);
            } else {
            	//logger.info(object.toString()+" is Object");            	
                return inicializarPropiedadesObjeto2(object);
            }
        }

    }


    @SuppressWarnings("rawtypes")
    private Collection inicializarColeccion2(Collection coleccion) throws InstantiationException, IllegalAccessException {
    	List list =new ArrayList();
        for (Object object : coleccion) {
        	list.add(inicializarSiEsNecesario2(object));
        }

        return list;
    }

    private Object inicializarPropiedadesObjeto2(Object object) throws InstantiationException, IllegalAccessException {
    	//logger.info(object.getClass().getName());   
    	if(object instanceof ByteBuddyInterceptor) {
    		return null;
    	}
    	Object objNew=object.getClass().newInstance();
        for (Field field : object.getClass().getDeclaredFields()) {
        	
        	if(field.getName().equals("categoria")) {
        		logger.info(field.getName());	
        	}
        	
            if (fieldEsSusceptibleDeInicializarse2(field)) {
                field.setAccessible(true);
                try {
                	//logger.info(field.getName());
                    field.set(objNew, inicializarSiEsNecesario2(field.get(object)));
                } catch (final Exception e) {
                    logger.error("Se ha producido un error al acceder al campo {} del objeto {}", new Object[] { field,
                            object }, e);
                    //throw new RuntimeException("Error al acceder a campo", e);
                }
            }
            else {
            	if(!field.getName().equals("serialVersionUID")) {
            		boolean accesible=field.isAccessible();
                	field.setAccessible(true);
                	Object value = field.get(object);
                	field.set(objNew, value);
                	field.setAccessible(accesible);
            	}
            	
            	
            }
        }

        return objNew;
    }



    private boolean fieldEsSusceptibleDeInicializarse2(final Field field) {
        if (Collection.class.isAssignableFrom(field.getType())) {
            return true;
        }

        if (!claseEsDeTipoBasico2(field.getType()) && !clasePertenceALaEspecificiacionJava2(field.getType())) {
            return true;
        }

        return false;
    }

    private boolean clasePertenceALaEspecificiacionJava2(final Class<?> clase) {
        final String clasePropiedad = clase.getCanonicalName();
        if (clasePropiedad.startsWith("java.") || clasePropiedad.startsWith("javax.")) {
            return true;
        }
        return false;
    }

    private boolean claseEsDeTipoBasico2(final Class<?> clase) {
        if (clase.isEnum() || clase.isPrimitive()) {
            return true;
        }
        return false;
    }
}