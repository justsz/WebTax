package webtax

class TagsTagLib {
	def redirectMainPage = {
		response.sendRedirect("${request.contextPath}/motu/list")
	  }
}
