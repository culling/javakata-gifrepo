# SPIKE - Implement Tests for active objects services
+ based on - https://developer.atlassian.com/server/framework/atlassian-sdk/getting-started-with-active-objects/#step-11--test-the-todo-service implement tests

# Create and serve home.vm form for POST details
+ Use renderer to send home.vm 
+ Add form to home.vm to POST to /gifrepo/api
+ Form Fields
   + name
   + url
+ Add test for form via cypress


# Create form servlet for upload - DONE
+ Add form servlet class 
+ Add servlet entry in atlassian-plugin.xml file
   + set url to /gifrepo
+ Add doGet method for servlet 
   + Return 501 not implemented 


# Change url for rest servlet to /gifrepo/api - DONE
+ change atlassian-plugin.xml url for GifServlet
+ change tests to use new url

# Replace Active Objects in servlet with Service - DONE
+ Replace constructor for servlet to one that uses service
+ Remove AO calls to make calls using .all and .add(name, url)

# Create service to do Active Object methods - DONE
+ Create GifService interface with methods
   + all()
   + add(name, url)
+ Annotate interface service as @Transactional
+ Create implementation of the service

# Add Gif and Active Objects - DONE
+ Add Gif interface
+ Add Gif Service interface
+ Add Gif Service implementation
+ Add AO to atlassian-plugin.xml

# Add GifServlet - DONE
+ Add GifServlet
+ Add Test for response from the get endpoint
+ Add screenshot for current plugin 

# Add Google Collections dependency - DONE
+ Add additional dependencies

# Add Cypress tests - DONE
+ Add package.json
+ Add cypress 
+ Add basic smoke test

# Add activeobjects-plugin - DONE
+ Add com.atlassian.activeobjects.activeobjects-plugin as dependency to pom.xml

# Create basic plugin - DONE 
+ run 'atlas-create-confluence-plugin'

# Initial commit - DONE
+ Add .gitignore 
+ Create repo