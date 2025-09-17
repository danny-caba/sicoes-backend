package pe.gob.osinergmin.sicoes.util.bean;

public class AlfrescoFile {
	String fullFilePath;
	String nodeRef;
	String uuid;

	public String getFullFilePath() {
		return fullFilePath;
	}

	public void setFullFilePath(String fullFilePath) {
		this.fullFilePath = fullFilePath;
	}

	public String getNodeRef() {
		return nodeRef;
	}

	public void setNodeRef(String nodeRef) {
		this.nodeRef = nodeRef;
		// Extraer UUID del nodeRef si viene en formato workspace://SpacesStore/UUID
		if (nodeRef != null && nodeRef.contains("workspace://SpacesStore/")) {
			this.uuid = nodeRef.substring(nodeRef.lastIndexOf("/") + 1);
		}
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
