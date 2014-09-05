from flux.models import Compound

# This file uses mysql database to replace the old ReactionDB
class CompoundDB:
    def is_a_valid_name(self, n):
        print "[LOG] Looking for long name [" + n + "]" + " in CompoundDB... ",
        try:
            c = Compound.objects.get(long_name__exact=n)
            print "Found!"
            return True
        except Compound.MultipleObjectsReturned:
            print "Duplicated long name!"
            return False
        except Compound.DoesNotExist:
            print "Not found!"
        try:
            print "Assuming it is a short name"
            c = Compound.objects.get(name__exact = n)
            if len(c.long_name) < 1:
		        print "Not found"
		        return False
            else:
                return True
        except Compound.DoesNotExist:
            return False

    def get_long_name(self, sname):
        try:
            c = Compound.objects.get(name__exact = sname)
            if len(c.long_name) < 1:
                a = c.alias
                c = Compound.objects.get(name__exact = a)
            return c.long_name
        except Compound.DoesNotExist:
            return sname
