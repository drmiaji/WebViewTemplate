function onWindowClick(e) {
    const tocTitle = document.querySelector(".toc .title");
    const tocList = document.getElementById("toc-ul");

    if (tocTitle && (e.target === tocTitle || tocTitle.contains(e.target))) {
        if (tocList) {
            tocList.style.display = tocList.style.display === "none" ? "block" : "none";
        }
    }
}

window.addEventListener("click", onWindowClick);