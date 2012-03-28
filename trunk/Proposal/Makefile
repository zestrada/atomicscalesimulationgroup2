PROJNAME=proposal

default: pdf

all:	ps pdf

once: 
	pdflatex $(PROJNAME)

bib:
	latex $(PROJNAME) ; bibtex $(PROJNAME); latex $(PROJNAME); latex $(PROJNAME)

ps:	bib report.ps

report.pdf:	
	pdflatex $(PROJNAME)

report.ps:	
	dvips -t letter -o $(PROJNAME).ps $(PROJNAME).dvi

pdf:	
	pdflatex $(PROJNAME) ; bibtex $(PROJNAME); pdflatex $(PROJNAME); pdflatex $(PROJNAME);

si:
	 pdflatex $(SINAME) ; bibtex $(SINAME); pdflatex $(SINAME); pdflatex $(SINAME);

clean:
	rm -f *.aux *.bbl *.blg $(PROJNAME).dvi $(PROJNAME).log $(PROJNAME).toc *~
