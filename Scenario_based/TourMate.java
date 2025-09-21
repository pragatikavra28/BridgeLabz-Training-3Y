using System;
using System.Collections.Generic;

namespace TourMate
{
    // IBookable Interface
    public interface IBookable
    {
        bool Book();
        bool Cancel();
    }

    // Base Trip Class
    public abstract class Trip : IBookable
    {
        public string Destination { get; protected set; }
        public decimal Budget { get; protected set; }
        public int Duration { get; protected set; }
        public List<Transport> Transports { get; protected set; }
        public List<Hotel> Hotels { get; protected set; }
        public List<Activity> Activities { get; protected set; }

        protected Trip(string destination, decimal budget, int duration)
        {
            Destination = destination;
            Budget = budget;
            Duration = duration;
            Transports = new List<Transport>();
            Hotels = new List<Hotel>();
            Activities = new List<Activity>();
        }

        public void AddTransport(Transport transport) => Transports.Add(transport);
        public void AddHotel(Hotel hotel) => Hotels.Add(hotel);
        public void AddActivity(Activity activity) => Activities.Add(activity);

        public decimal CalculateTotalCost()
        {
            decimal transportCost = Transports.Sum(t => t.Cost);
            decimal hotelCost = Hotels.Sum(h => h.Cost);
            decimal activityCost = Activities.Sum(a => a.Cost);
            
            return transportCost + hotelCost + activityCost;
        }

        public abstract bool Book();
        public abstract bool Cancel();

        public virtual void DisplayItinerary()
        {
            Console.WriteLine($"Destination: {Destination}");
            Console.WriteLine($"Duration: {Duration} days");
            Console.WriteLine($"Total Cost: {CalculateTotalCost():C}");
        }
    }

    // Derived Trip Types
    public class InternationalTrip : Trip
    {
        public bool VisaRequired { get; private set; }
        public string PassportNumber { get; private set; }

        public InternationalTrip(string destination, decimal budget, int duration, 
            bool visaRequired, string passportNumber) 
            : base(destination, budget, duration)
        {
            VisaRequired = visaRequired;
            PassportNumber = passportNumber;
        }

        public override bool Book()
        {
            // International booking logic with visa checks
            if (VisaRequired && string.IsNullOrEmpty(PassportNumber))
            {
                Console.WriteLine("Cannot book international trip without passport number");
                return false;
            }
            
            Console.WriteLine($"Booking international trip to {Destination}");
            return true;
        }

        public override bool Cancel()
        {
            // International cancellation may have different policies
            Console.WriteLine($"Cancelling international trip to {Destination}");
            return true;
        }

        public override void DisplayItinerary()
        {
            base.DisplayItinerary();
            Console.WriteLine($"Visa Required: {VisaRequired}");
            Console.WriteLine($"Passport: {PassportNumber}");
        }
    }

    public class DomesticTrip : Trip
    {
        public string State { get; private set; }

        public DomesticTrip(string destination, decimal budget, int duration, string state) 
            : base(destination, budget, duration)
        {
            State = state;
        }

        public override bool Book()
        {
            // Domestic booking logic
            Console.WriteLine($"Booking domestic trip to {Destination}, {State}");
            return true;
        }

        public override bool Cancel()
        {
            // Domestic cancellation logic
            Console.WriteLine($"Cancelling domestic trip to {Destination}");
            return true;
        }

        public override void DisplayItinerary()
        {
            base.DisplayItinerary();
            Console.WriteLine($"State: {State}");
        }
    }

    // Service Classes with Encapsulated Costs
    public class Transport : IBookable
    {
        private decimal cost;
        public string Type { get; private set; }
        public string Details { get; private set; }

        public Transport(string type, decimal cost, string details)
        {
            Type = type;
            this.cost = cost;
            Details = details;
        }

        public decimal Cost => cost;

        public bool Book()
        {
            Console.WriteLine($"Booking {Type} transport: {Details}");
            return true;
        }

        public bool Cancel()
        {
            Console.WriteLine($"Cancelling {Type} transport");
            return true;
        }
    }

    public class Hotel : IBookable
    {
        private decimal cost;
        public string Name { get; private set; }
        public int Stars { get; private set; }

        public Hotel(string name, decimal cost, int stars)
        {
            Name = name;
            this.cost = cost;
            Stars = stars;
        }

        public decimal Cost => cost;

        public bool Book()
        {
            Console.WriteLine($"Booking hotel: {Name} ({Stars} stars)");
            return true;
        }

        public bool Cancel()
        {
            Console.WriteLine($"Cancelling hotel: {Name}");
            return true;
        }
    }

    public class Activity : IBookable
    {
        private decimal cost;
        public string Name { get; private set; }
        public string Description { get; private set; }

        public Activity(string name, decimal cost, string description)
        {
            Name = name;
            this.cost = cost;
            Description = description;
        }

        public decimal Cost => cost;

        public bool Book()
        {
            Console.WriteLine($"Booking activity: {Name}");
            return true;
        }

        public bool Cancel()
        {
            Console.WriteLine($"Cancelling activity: {Name}");
            return true;
        }
    }
}