package webtax

class TagsTagLib {
	
	static namespace = "t"
	
	def redirectMainPage = {
		response.sendRedirect("${request.contextPath}/motu/list")
	  }
	
}
