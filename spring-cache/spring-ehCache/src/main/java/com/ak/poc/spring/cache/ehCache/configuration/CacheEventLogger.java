package com.ak.poc.spring.cache.ehCache.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

public class CacheEventLogger implements CacheEventListener{

    private static final Logger log = LoggerFactory.getLogger(CacheEventLogger.class);

	@Override
	public void notifyElementRemoved(Ehcache cache, Element element) throws CacheException {
		log.info("Cache event notifyElementRemoved "+element.toString());
		
	}

	@Override
	public void notifyElementPut(Ehcache cache, Element element) throws CacheException {
		log.info("Cache event notifyElementPut "+element.toString());
		
	}

	@Override
	public void notifyElementUpdated(Ehcache cache, Element element) throws CacheException {
		log.info("Cache event notifyElementUpdated "+element.toString());
		
	}

	@Override
	public void notifyElementExpired(Ehcache cache, Element element) {
		log.info("Cache event notifyElementExpired "+element.toString());
		
	}

	@Override
	public void notifyElementEvicted(Ehcache cache, Element element) {
		log.info("Cache event notifyElementEvicted "+element.toString());
		
	}

	@Override
	public void notifyRemoveAll(Ehcache cache) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

   @Override
public Object clone() throws CloneNotSupportedException {
	// TODO Auto-generated method stub
	return super.clone();
}

}
