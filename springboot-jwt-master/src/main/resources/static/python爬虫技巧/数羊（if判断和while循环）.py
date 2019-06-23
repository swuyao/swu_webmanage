from urllib.request import urlopen
from pdfminer.pdfinterp import PDFResourceManager,process_pdf
from pdfminer.converter import TextConverter
from pdfminer.layout import LAParams
from io import StringIO
from io import open
import re
import os
import warnings
warnings.filterwarnings("ignore")
def get(file):
   pdffile=open(file,"rb")
   rsrcmgr=PDFResourceManager()
   retstr=StringIO()
   laparams=LAParams()
   device=TextConverter(rsrcmgr,retstr,laparams=laparams)
   process_pdf(rsrcmgr,device,pdffile)
   device.close()
   content=retstr.getvalue()
   retstr.close()
   return content
regex1=re.compile("(供应商名称[\s\S]*?)合计")
ls = os.listdir('K:\\pdf\\')
for dr in ls:
   path = "K:\\pdf\\" + dr
   docs = os.listdir(path)
   for doc in docs:
      file = path + "\\" +  doc
      try:
        content = get(file)
        content = regex1.findall(content)
        print(dr + "\\" + doc ,content[0].replace("\n\n",""))
      except:
        pass