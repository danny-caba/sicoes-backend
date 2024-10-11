package pe.gob.osinergmin.sicoes.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import pe.gob.osinergmin.sicoes.model.BaseModel;
import pe.gob.osinergmin.sicoes.util.PageUtilImpl;
import pe.gob.osinergmin.sicoes.util.Raml;


@Aspect
@Component
public class AOPRest {

	Logger logger = LogManager.getLogger(AOPRest.class);
	
	@Autowired
	ObjectMapper objectMapper; 
	HashMap<String,List<String>> ramls=new HashMap<String, List<String>>();
	
	public List<String> getRamls(String nombreRaml) throws IOException{
//		logger.info("INICIO LOG PROPERTIES");
		List<String> salida=ramls.get(nombreRaml);
		if(salida==null) {
			InputStream is=getClass().getClassLoader().getResourceAsStream("response"+File.separator+nombreRaml);
//		    logger.info("getFile:"+getClass().getClassLoader().getResourceAsStream("response"+File.separator+nombreRaml));
//		    logger.info("FIN LOG PROPERTIES");
//		    logger.info("FIN LOG PROPERTIES");
		    if(is==null) return null;
		    Properties properties=new Properties();
		    properties.load(is);
		    Iterator<Object> it=properties.keySet().iterator();    
		    List<String> keys=new ArrayList<>();
		    while(it.hasNext()) {
		    	Object value=it.next();	    	
		    	keys.add(value.toString());
		    }
		    Collections.sort(keys);
		    ramls.put(nombreRaml, keys);
		}
	    return ramls.get(nombreRaml);
	}
	
	@Around("execution(public * *(..)) && @annotation(pe.gob.osinergmin.sicoes.util.Raml)")
	public Object aplicandoRaml(ProceedingJoinPoint joinPoint) throws Throwable {
		HashMap<String, Object> salida=new HashMap<>();
		Object obj=joinPoint.proceed();
		if(obj==null) return null;
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
	    Method method = signature.getMethod();
	    Class classReturn=signature.getReturnType();
	    Raml raml = method.getAnnotation(Raml.class); 
	    List<String> keys=getRamls(raml.value());
	    if(keys==null) return obj;
	    HashMap<String, Object> keysMapRoot=new HashMap<>();
	    HashMap<String, Object> keysMapActual=null;
	    for(String campo:keys) {
	    	keysMapActual=keysMapRoot;
	    	
	    	
	    	String[] camposDivididos=campo.split("\\.");
	    	for(int i=0;i<camposDivididos.length;i++) {
	    		String campoDividido=camposDivididos[i];
	    		if(keysMapActual.get(campoDividido)!=null) {
	    			HashMap<String, Object> keyMapsAux=(HashMap<String, Object>)keysMapActual.get(campoDividido);	    			
	    			keysMapActual=keyMapsAux;
	    		}else if(keysMapActual.get(campoDividido)==null&&i!=camposDivididos.length-1) {
	    			HashMap<String, Object> keyMapsNew=new HashMap<>();	    			
	    			keysMapActual.put(campoDividido,keyMapsNew );
	    			keysMapActual=keyMapsNew;
	    		}else if(i==camposDivididos.length-1) {
	    			keysMapActual.put(campoDividido, campoDividido);
	    		}
	    	}
	    }
	    
	    
	  
	    salida= obtenerObjetoGeneral(obj,salida,keysMapRoot);
	    if(obj instanceof Page) {
	    	Page<BaseModel> page=(Page<BaseModel>)obj;
	    	if(page.getContent().isEmpty()) {
	    		return new PageImpl(new ArrayList(),page.getPageable(),page.getTotalElements());	    			    		
	    	}else {
	    		Class<? extends Object> dataClass=page.getContent().get(0).getClass();
	    		String json=objectMapper.writeValueAsString(salida.get("content"));
	    		CollectionType typeReference =	TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, dataClass);
	    		ArrayList<? extends Object> list= objectMapper.readValue(json,typeReference);
	    		if(obj instanceof PageUtilImpl) {
	    			PageUtilImpl<? extends Object> aux0=(PageUtilImpl<? extends Object>)page;
	    			PageUtilImpl<? extends Object> aux= new PageUtilImpl(list,page.getPageable(),page.getTotalElements());
	    			aux.setTotalMonto(aux0.getTotalMonto());
	    			aux.setAnio(aux0.getAnio());
	    			aux.setMes(aux0.getMes());
	    			aux.setDia(aux0.getDia());
	    			return aux;
	    		}else {
	    			return new PageImpl(list,page.getPageable(),page.getTotalElements());
	    		}	    			    		
	    	}
	    }
	    String json=objectMapper.writeValueAsString(salida);
		return objectMapper.readValue(json, classReturn);
	}
	
	
	public boolean esCampo(String value,Map<String, Object> keysMapRoot) {
		Object val=keysMapRoot.get(value);
		return (val instanceof String);
	}
	private boolean esObjeto(String value,Map<String, Object> keysMapRoot) {
		Object val=keysMapRoot.get(value);
		return !(val==null||value.contains("[]")|| val instanceof String);
	}
	
	private boolean esMapa(String value, Map<String, Object> keysMapRoot) {
		Object val=keysMapRoot.get(value);
		return (val!=null&&value.contains("[[]]"));
	}


	private HashMap<String, Object> obtenerObjetoGeneral(Object obj, Object objPadre,HashMap<String, Object> keysMapRoot) throws Exception{
		HashMap<String, Object> salida=new HashMap<>();
		Iterator<String> keysIt=keysMapRoot.keySet().iterator();
	    while(keysIt.hasNext()) {
	    	String campo=keysIt.next();
	    	    	
			if(esCampo(campo,keysMapRoot)) {
	    		salida.put(campo, getValue(campo,obj));	
	    	}else if(esObjeto(campo,keysMapRoot)){
	        	Object objetoActual=getValue(campo, obj);
	        	if(objetoActual!=null) {	        		
	        		HashMap<String, Object> keysMap=(HashMap<String, Object>)keysMapRoot.get(campo);
	        		salida.put(campo, obtenerObjetoGeneral(objetoActual,salida.get(campo),keysMap));
	    		}
	    	}else if(esMapa(campo,keysMapRoot)) {
	    		String campoFormateado=campo.substring(0,campo.length()-4);
	    		Object objetoActual=getValue(campoFormateado, obj);
	        	if(objetoActual!=null) {	        		
	        		HashMap<String, Object> keysMap=(HashMap<String, Object>)keysMapRoot.get(campo);
	        		salida.put(campoFormateado, obtenerObjetoGeneral(objetoActual,salida.get(campo),keysMap));
	    		}
	    	}else {	    		
	    		String campoFormateado=campo.substring(0,campo.length()-2);
	    		boolean esVacio=false;
	    		Object valor=getValue(campoFormateado,obj);
	    		
		        	HashMap<String, Object> keysMap=(HashMap<String, Object>)keysMapRoot.get(campo);
		        	List<Object> listAux=new ArrayList<>();
		        	if(valor instanceof Collection) {	
			        	Collection list=(Collection) valor;
			        	if(list!=null) {
				        	for(Object objAux:list) {
				        		Object val=obtenerObjetoGeneral(objAux,salida.get(campo),keysMap);
				        		if(val!=null) {
				        			esVacio=true;
				        		}
				        		listAux.add(val);
				        	}
			        	}
		        	}else {
		        		Map map =(HashMap)valor;
		        		if(map!=null) {
			        		Object val=obtenerObjetoGeneral(map,salida.get(campo),keysMap);
					        if(val!=null) {
					        	esVacio=true;
					        }
					        listAux.add(val);
		        		}
			        	
		        	}	
		        	if(!esVacio&&listAux.isEmpty()) {
		        		listAux=null;
		        	}
		        	salida.put(campoFormateado, listAux);
	        	
	        	
	    	}
		}
		return salida;
	}
	
	Object getValueMethod(Object obj,Class<? extends Object> c,String prefix,String nombre)throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Method getter=c.getMethod(prefix+nombre);
    	return getter.invoke(obj);
	}
	
	Object getValueMethod(Object obj,Class<? extends Object> c,String nombre)throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		try {						
	    	return getValueMethod(obj,c,"get",nombre);
    	}catch (NoSuchMethodException e) {
    		try {
    			return getValueMethod(obj,c,"is",nombre);
    		}catch (NoSuchMethodException ex) {
    			return null;
			}
		}
		
	}
	
	Object getValueDate(Object value,String campo,Class<? extends Object> c){
		SimpleDateFormat sdf=null;
		try {
    		Field field=c.getDeclaredField(campo);
    		JsonFormat jsonFormat=field.getAnnotation(JsonFormat.class);
    		sdf=new SimpleDateFormat(jsonFormat.pattern());	
		}catch (Exception e) {
			sdf=new SimpleDateFormat("dd/MM/yyyy");
		}
		
		return sdf.format(value);
	}


	private Object getValue(String campo,Object obj){
		Object value=null;
		try {
			if(obj==null) return null;
			Class<? extends Object> c = obj.getClass();
			if(obj instanceof Map) {
				Map<String,Object> map=(Map) obj;
				value =map.get(campo);
			}else {
				String nombre=(campo.charAt(0)+"").toUpperCase()+campo.substring(1);
				value =getValueMethod( obj, c, nombre);
			}
	    	if(value instanceof Date) {	    		
	    		value= getValueDate(value,campo, c);
	    	}
    	}catch (Exception e) {
    		logger.error(e.getMessage(), e);
    		
		}
		return value;
	}
	
	private Object clonar(Object obj) throws Exception {
		
		if(obj instanceof PageImpl) {
			PageImpl<Object> pageImpl=(PageImpl)obj;
			List<Object> content=clonar(pageImpl.getContent());
			return new PageImpl<Object>(content,pageImpl.getPageable(),pageImpl.getTotalElements());
	    }
		if(obj instanceof Collection) {
			return clonar(obj);
		}else {
			return obj.getClass().getDeclaredConstructor().newInstance();
		}
	}

	private List<Object> clonar(Collection coleccion) throws Exception {
    	List<Object> collectionSalida =new ArrayList<>();
	        for (Object object : coleccion) {
	        	collectionSalida.add(clonar(object));
	        }
	        return collectionSalida;
	}
}
