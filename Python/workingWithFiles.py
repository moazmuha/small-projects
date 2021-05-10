class TextFile:

    def __init__(self, filename: str):
        self.filename = filename
        self.list = self._create_list()

    def _create_list(self):
        f = open(self.filename)
        Lines = f.readlines()
        list_wrds = []
        for line in Lines:
            list_wrds.append(line.split(' '))
        f.close()