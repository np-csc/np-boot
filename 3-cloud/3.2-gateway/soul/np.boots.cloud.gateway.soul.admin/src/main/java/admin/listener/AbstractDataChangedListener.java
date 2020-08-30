/*
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package admin.listener;

import admin.service.RuleService;
import admin.service.SelectorService;
import org.apache.commons.collections4.CollectionUtils;
import admin.service.AppAuthService;
import admin.service.MetaDataService;
import admin.service.PluginService;
import admin.service.RuleService;
import admin.service.SelectorService;
import org.dromara.soul.common.dto.AppAuthData;
import org.dromara.soul.common.dto.ConfigData;
import org.dromara.soul.common.dto.MetaData;
import org.dromara.soul.common.dto.PluginData;
import org.dromara.soul.common.dto.RuleData;
import org.dromara.soul.common.dto.SelectorData;
import org.dromara.soul.common.enums.ConfigGroupEnum;
import org.dromara.soul.common.enums.DataEventTypeEnum;
import org.dromara.soul.common.utils.GsonUtils;
import org.dromara.soul.common.utils.Md5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Abstract class for ConfigEventListener.
 * As we think that the md5 value of the in-memory data is the same as the md5 value of the database,
 * although it may be a little different, but it doesn't matter, we will have thread to periodica
 * pull the data in the database.
 *
 * @author huangxiaofeng
 * @since 2.0.0
 */
@SuppressWarnings("all")
public abstract class AbstractDataChangedListener implements DataChangedListener, InitializingBean {
    
    /**
     * The constant CACHE.
     */
    protected static final ConcurrentHashMap<String, ConfigDataCache> CACHE = new ConcurrentHashMap<>();
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDataChangedListener.class);
    
    @Resource
    private AppAuthService appAuthService;
    
    /**
     * The Plugin service.
     */
    @Resource
    private PluginService pluginService;
    
    /**
     * The Rule service.
     */
    @Resource
    private RuleService ruleService;
    
    /**
     * The Selector service.
     */
    @Resource
    private SelectorService selectorService;
    
    @Resource
    private MetaDataService metaDataService;
    
    /**
     * fetch configuration from database.
     *
     * @param groupKey the group key
     * @return the configuration data
     */
    public ConfigData<?> fetchConfig(final ConfigGroupEnum groupKey) {
        ConfigDataCache config = CACHE.get(groupKey.name());
        switch (groupKey) {
            case ConfigGroupEnum.APP_AUTH:
                return new ConfigData<>(config.getMd5(), config.getLastModifyTime(), appAuthService.listAll());
            case ConfigGroupEnum.PLUGIN:
                return new ConfigData<>(config.getMd5(), config.getLastModifyTime(), pluginService.listAll());
            case ConfigGroupEnum.RULE:
                return new ConfigData<>(config.getMd5(), config.getLastModifyTime(), ruleService.listAll());
            case ConfigGroupEnum.SELECTOR:
                return new ConfigData<>(config.getMd5(), config.getLastModifyTime(), selectorService.listAll());
            case ConfigGroupEnum.META_DATA:
                return new ConfigData<>(config.getMd5(), config.getLastModifyTime(), metaDataService.listAll());
            default:
                throw new IllegalStateException("Unexpected groupKey: " + groupKey);
        }
    }
    
    @Override
    public void onAppAuthChanged(final List<AppAuthData> changed, final DataEventTypeEnum eventType) {
        if (CollectionUtils.isEmpty(changed)) {
            return;
        }
        this.updateAppAuthCache();
        this.afterAppAuthChanged(changed, eventType);
    }
    
    @Override
    public void onMetaDataChanged(final List<MetaData> changed, final DataEventTypeEnum eventType) {
        if (CollectionUtils.isEmpty(changed)) {
            return;
        }
        this.updateMetaDataCache();
        this.afterMetaDataChanged(changed, eventType);
    }
    
    
    /**
     * After meta data changed.
     *
     * @param changed   the changed
     * @param eventType the event type
     */
    protected void afterMetaDataChanged(final List<MetaData> changed, final DataEventTypeEnum eventType) {
    }
    
    /**
     * After app auth changed.
     *
     * @param changed   the changed
     * @param eventType the event type
     */
    protected void afterAppAuthChanged(final List<AppAuthData> changed, final DataEventTypeEnum eventType) {
    }
    
    @Override
    public void onPluginChanged(final List<PluginData> changed, final DataEventTypeEnum eventType) {
        if (CollectionUtils.isEmpty(changed)) {
            return;
        }
        this.updatePluginCache();
        this.afterPluginChanged(changed, eventType);
    }
    
    /**
     * After plugin changed.
     *
     * @param changed   the changed
     * @param eventType the event type
     */
    protected void afterPluginChanged(final List<PluginData> changed, final DataEventTypeEnum eventType) {
    }
    
    @Override
    public void onRuleChanged(final List<RuleData> changed, final DataEventTypeEnum eventType) {
        if (CollectionUtils.isEmpty(changed)) {
            return;
        }
        this.updateRuleCache();
        this.afterRuleChanged(changed, eventType);
    }
    
    /**
     * After rule changed.
     *
     * @param changed   the changed
     * @param eventType the event type
     */
    protected void afterRuleChanged(final List<RuleData> changed, final DataEventTypeEnum eventType) {
    }
    
    @Override
    public void onSelectorChanged(final List<SelectorData> changed, final DataEventTypeEnum eventType) {
        if (CollectionUtils.isEmpty(changed)) {
            return;
        }
        this.updateSelectorCache();
        this.afterSelectorChanged(changed, eventType);
    }
    
    /**
     * After selector changed.
     *
     * @param changed   the changed
     * @param eventType the event type
     */
    protected void afterSelectorChanged(final List<SelectorData> changed, final DataEventTypeEnum eventType) {
    }
    
    @Override
    public final void afterPropertiesSet() {
        updateAppAuthCache();
        updatePluginCache();
        updateRuleCache();
        updateSelectorCache();
        updateMetaDataCache();
    }
    
    /**
     * Update selector cache.
     */
    protected void updateSelectorCache() {
        String json = GsonUtils.getInstance().toJson(selectorService.listAll());
        String group = ConfigGroupEnum.SELECTOR.name();
        CACHE.put(group, new ConfigDataCache(group, Md5Utils.md5(json), System.currentTimeMillis()));
    }
    
    /**
     * Update rule cache.
     */
    protected void updateRuleCache() {
        String json = GsonUtils.getInstance().toJson(ruleService.listAll());
        String group = ConfigGroupEnum.RULE.name();
        CACHE.put(group, new ConfigDataCache(group, Md5Utils.md5(json), System.currentTimeMillis()));
    }
    
    /**
     * Update plugin cache.
     */
    protected void updatePluginCache() {
        String json = GsonUtils.getInstance().toJson(pluginService.listAll());
        String group = ConfigGroupEnum.PLUGIN.name();
        CACHE.put(group, new ConfigDataCache(group, Md5Utils.md5(json), System.currentTimeMillis()));
    }
    
    /**
     * Update app auth cache.
     */
    protected void updateAppAuthCache() {
        String json = GsonUtils.getInstance().toJson(appAuthService.listAll());
        String group = ConfigGroupEnum.APP_AUTH.name();
        CACHE.put(group, new ConfigDataCache(group, Md5Utils.md5(json), System.currentTimeMillis()));
    }
    
    /**
     * Update meta data cache.
     */
    protected void updateMetaDataCache() {
        String json = GsonUtils.getInstance().toJson(metaDataService.listAll());
        String group = ConfigGroupEnum.META_DATA.name();
        CACHE.put(group, new ConfigDataCache(group, Md5Utils.md5(json), System.currentTimeMillis()));
    }
    
}
