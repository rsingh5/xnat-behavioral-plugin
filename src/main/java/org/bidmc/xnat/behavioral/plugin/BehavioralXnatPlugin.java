package org.bidmc.xnat.behavioral.plugin;

import org.nrg.framework.annotations.XnatDataModel;
import org.nrg.framework.annotations.XnatPlugin;
import org.nrg.xdat.bean.BehavioralScoresBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@XnatPlugin(name="SHARP Behavioral Data", value="BehavioralPlugin", description="Extend XNAT for behavioral data",  entityPackages = "org.bidmc.xnat.behavioral.subjectmapping",  
dataModels = {@XnatDataModel(value = BehavioralScoresBean.SCHEMA_ELEMENT_NAME,
                             singular = "Behavioral Collection",
                             plural = "Behavioral Collections")})
@ComponentScan({"org.bidmc.xnat.behavioral.subjectmapping.preferences",
"org.bidmc.xnat.behavioral.subjectmapping.repositories",
"org.bidmc.xnat.behavioral.subjectmapping.rest",
"org.bidmc.xnat.behavioral.subjectmapping.services.impl"})

public class BehavioralXnatPlugin {
	 @Bean
	    public String workshopPluginMessage() {
	        return "Hello there from the workshop plugin!";
	    }
}
