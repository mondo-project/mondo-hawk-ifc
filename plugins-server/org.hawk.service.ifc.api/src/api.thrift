namespace java org.hawk.service.ifc.api

enum IFCExportStatus {
		/* The job has been cancelled. */ CANCELLED 
		/* The job is completed. */ DONE 
		/* The job has failed. */ FAILED 
		/* The job is currently running. */ RUNNING 
		/* The job has been scheduled but has not started yet. */ SCHEDULED 
}


struct IFCExportJob {
	 /*  */ 1: required string jobID,
	 /*  */ 2: required IFCExportStatus status,
	 /*  */ 3: required string message,
}

struct IFCExportOptions {
	 /* The repository for the query (or * for all repositories). */ 1: optional string repositoryPattern = "*",
	 /* The file patterns for the query (e.g. *.uml). */ 2: optional list<string> filePatterns,
	 /* If set and not empty, only the specified metamodels, types and features will be fetched. Otherwise, everything that is not excluded will be fetched. The string '*' can be used to refer to all types within a metamodel or all fields within a type. */ 3: optional map<string,map<string,set<string>>> includeRules,
	 /* If set and not empty, the mentioned metamodels, types and features will not be fetched. The string '*' can be used to refer to all types within a metamodel or all fields within a type. */ 4: optional map<string,map<string,set<string>>> excludeRules,
}

/* IFC export facility for getting IFC models from the Hawk server. */
service IFCExport {
  /* Export part of a Hawk index in IFC STEP format. Auth needed: Yes */
  IFCExportJob exportAsSTEP(
	/*  */ 1: required string hawkInstance,
	/*  */ 2: required IFCExportOptions options,
  )
	
  /* List all the previously scheduled IFC export jobs. Auth needed: Yes */
  list<IFCExportJob> getJobs(
  )
	
  /* Retrieve the current status of the job with the specified ID. Auth needed: Yes */
  IFCExportJob getJobStatus(
	/*  */ 1: required string jobID,
  )
	
  /* Cancel the job with the specified ID. Auth needed: Yes */
  bool killJob(
	/*  */ 1: required string jobID,
  )
	
}

